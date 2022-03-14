import bot.TalksBot
import logger.Log
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main(args: Array<String>) {
    val botSession = DefaultBotSession()
    val botsApi = TelegramBotsApi(botSession::class.java)
    try {
        val env = System.getenv()
        val botInstance = TalksBot(name = env["TALKS_BOT_NAME"]!!, token = env["TALKS_BOT_TOKEN"]!!)
        botsApi.registerBot(botInstance)
        Log.d("Talks bot registered")
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}