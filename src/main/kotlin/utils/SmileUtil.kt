package utils

object SmileUtil {
    fun getSmileString(type: Smile): String {
        return when(type) {
            Smile.HAPPY -> { "ヽ(・ω・)ﾉ" }
            Smile.FEAR -> { "(」°ロ°)」" }
            Smile.DONNO -> { "¯\\_(ツ)_/¯" }
            Smile.SAD -> { "(╮°-°)╮" }
            Smile.GIVE -> { "( ＾▽＾)っ" }
            Smile.THIS -> { "(　･ω･)☞" }
            Smile.LISTEN -> { "∠( ᐛ 」∠)＿" }
            Smile.YESSIR -> { "(￣^￣)ゞ" }
            Smile.PLS -> { "(づ◡﹏◡)づ" }
        }
    }
}

enum class Smile { HAPPY, FEAR, DONNO, SAD, GIVE, THIS, LISTEN, YESSIR, PLS }