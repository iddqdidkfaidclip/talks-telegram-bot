package repository.database

import java.sql.Connection
import java.sql.DriverManager

class Database {
    companion object {
        private val INSTANCE: Connection? = null

        fun getConnection(url: String): Connection {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DriverManager.getConnection(url)
            }
        }
    }
}