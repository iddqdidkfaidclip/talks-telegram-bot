package bot

import bot.parser.MessageParser
import logger.Log
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

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
            val nameSender = update?.message?.from?.firstName
            val chatId = update?.message?.chatId.toString()
            val messageCommand = update?.message?.text ?: return

            val message = MessageParser.parseMessage(messageCommand)
            val sendDocument = SendMessage().apply {
                this.chatId = chatId
                this.text = "msg: $nameSender, $chatId, $messageCommand // ${message}"
            }
            execute(sendDocument)
            Log.d("message sent")
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e(ex)
        }
    }

}