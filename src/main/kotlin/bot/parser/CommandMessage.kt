package bot.parser

enum class CommandMessage(val msg: String) {
    PICK("/pick"),
    SHOW_ALL("/show"),
    UNKNOWN(""),
    NEW_THEME("/new"),
    CLEAR_THEME("/clear"),
    HELP("/help")
}