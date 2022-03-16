package utils

object HelpDocUtil {
    fun getDocString(): String {
        val doc = java.lang.StringBuilder()
        doc.append("${SmileUtil.getSmileString(Smile.YESSIR)}\nВот что я умею:\n")
        doc.append("/help : Ну это ты уже умеешь\n")
        doc.append("/new <тема> : Добавлю новую тему\n")
        doc.append("/show : Покажу полный список тем\n")
        doc.append("/pick : Достану рандомную тему и удалю её из бэклога тем\n")
        doc.append("/clear : Удалю все твои темы. Вообще все!\n")
        return doc.toString()
    }
}