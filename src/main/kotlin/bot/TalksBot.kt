package bot

import bot.parser.MessageParser
import bot.parser.TalkBotCommand
import logger.Log
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import repository.TalksRepository
import repository.database.entity.ThemeEntity
import utils.HelpDocUtil
import utils.Smile
import utils.SmileUtil

class TalksBot(val name: String, val token: String): TelegramLongPollingBot() {

    override fun getBotToken(): String {
        return token
    }

    override fun getBotUsername(): String {
        return name
    }

    override fun onUpdateReceived(update: Update?) {
        // We check if the update has a message and the message has text
        try {
            val userId = update?.message?.from?.id ?: 0L
            val chatId = update?.message?.chatId.toString()
            val messageCommand = update?.message?.text ?: return

            val botCommand = MessageParser.parseMessage(messageCommand)
            val processingResult = processCommand(botCommand, userId)
            val preparedBotResponse = getResponseFromProcessingResult(processingResult)
            val sendDocument = SendMessage().apply {
                this.chatId = chatId
                this.text = preparedBotResponse
            }
            execute(sendDocument)
            Log.d("message sent")
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e(ex)
        }
    }

    private fun getResponseFromProcessingResult(processingResult: ProcessingResult): String {
        return when (processingResult) {
            is ProcessingResult.Fail -> processingResult.errorMessage
            is ProcessingResult.ThemeAdded -> {
                "Новая тема добавлена, выглядит интересно\n${SmileUtil.getSmileString(Smile.HAPPY)}"
            }
            is ProcessingResult.ListOfThemes -> {
                val resultString = java.lang.StringBuilder()
                resultString.append("${SmileUtil.getSmileString(Smile.GIVE)} Ваши темы:\n")
                processingResult.themes.forEachIndexed { index, themeEntity ->
                    resultString.append("$index) ${themeEntity.title}\n")
                }
                resultString.toString()
            }
            is ProcessingResult.PickedTheme -> {
                "Вот эту обсуди ${SmileUtil.getSmileString(Smile.THIS)} ${processingResult.theme.title}"
            }
            is ProcessingResult.ThemesCleared -> {
                "Я удолил абсалютна всё\n${SmileUtil.getSmileString(Smile.LISTEN)}"
            }
            is ProcessingResult.Help -> HelpDocUtil.getDocString()
        }
    }

    private fun processCommand(botCommand: TalkBotCommand, userId: Long): ProcessingResult {
        return when (botCommand) {
            is TalkBotCommand.Help -> {
                ProcessingResult.Help
            }
            is TalkBotCommand.NewTheme -> {
                val result = TalksRepository.addTheme(botCommand.theme, userId)
                if (result != null) ProcessingResult.ThemeAdded(result)
                else ProcessingResult.Fail("Чот я не смог добавить тему.\n${SmileUtil.getSmileString(Smile.FEAR)}")
            }
            is TalkBotCommand.ShowAll -> {
                ProcessingResult.ListOfThemes(
                    TalksRepository.getAllThemes(userId)
                )
            }
            is TalkBotCommand.ClearAll -> {
                val clearResult = TalksRepository.clearAllThemes(userId)
                if (clearResult) ProcessingResult.ThemesCleared
                else ProcessingResult.Fail("А было ли что удалять?\n${SmileUtil.getSmileString(Smile.DONNO)}")
            }
            is TalkBotCommand.PickRandom -> {
                val result = TalksRepository.getRandomTheme(userId)
                if (result == null) ProcessingResult.Fail("Кажется я не нашёл ни одной темы\n${SmileUtil.getSmileString(Smile.SAD)}")
                else ProcessingResult.PickedTheme(result)
            }
            else -> ProcessingResult.Fail("Я не понимаю что происходит, давай почитаем хелп? /help\n${SmileUtil.getSmileString(Smile.PLS)}")
        }
    }
}


sealed class ProcessingResult {
    class Fail(val errorMessage: String): ProcessingResult()
    class ListOfThemes(val themes: List<ThemeEntity>): ProcessingResult()
    class ThemeAdded(val themeId: Int): ProcessingResult()
    class PickedTheme(val theme: ThemeEntity): ProcessingResult()
    object Help : ProcessingResult()
    object ThemesCleared : ProcessingResult()
}