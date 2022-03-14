package bot.parser

sealed class TalkBotCommand {
    object Unknown : TalkBotCommand()
    object Help : TalkBotCommand()
    class NewTheme(val theme: String) : TalkBotCommand()
    class Clear(vararg ThemeIds: Int) : TalkBotCommand()
    object ClearAll : TalkBotCommand()
    object PickRandom : TalkBotCommand()
    object ShowAll : TalkBotCommand()
}
