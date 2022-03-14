package logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Log {
    private val log: Logger = LoggerFactory.getLogger("main")

    fun d(msg: String) {
        log.debug(msg)
    }
    fun e(ex: Throwable) {
        log.error("error: ${ex.message}")
    }

}