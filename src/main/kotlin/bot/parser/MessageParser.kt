package bot.parser

object MessageParser {

    fun parseMessage(msg: String): TalkBotCommand {
        return when {
            msg == CommandMessage.HELP.msg -> TalkBotCommand.Help
            msg == CommandMessage.PICK.msg -> TalkBotCommand.PickRandom
            msg == CommandMessage.SHOW_ALL.msg -> TalkBotCommand.ShowAll
            msg.startsWith(CommandMessage.CLEAR_THEME.msg) -> {
                val args = msg.substringAfter(CommandMessage.CLEAR_THEME.msg, "")
                if (args.trim().isEmpty()) TalkBotCommand.ClearAll
                else {
                    val themeIds = args.split(" ").mapNotNull { it.toIntOrNull() }
                    TalkBotCommand.Clear(*themeIds.toIntArray())
                }
            }
            msg.startsWith(CommandMessage.NEW_THEME.msg) -> {
                val newThemeTitle = msg.substringAfter(CommandMessage.NEW_THEME.msg)
                TalkBotCommand.NewTheme(newThemeTitle)
            }
            else -> TalkBotCommand.Unknown
        }
    }
}