package repository.database

import org.ktorm.database.Database
import org.ktorm.database.asIterable
import org.ktorm.database.use
import org.ktorm.dsl.*
import org.ktorm.entity.*
import repository.database.entity.ThemeEntity
import repository.database.entity.Themes

class DatabaseManager {
    private val themesDatabase: Database

    init {
        val jdbcUrl = System.getenv()["JDBC_DATABASE_URL"]
        themesDatabase = Database.connect(jdbcUrl!!)
    }

    fun getAllThemes(userId: Long): List<ThemeEntity> {
        return themesDatabase.sequenceOf(Themes).filter { it.userId eq userId }.sortedBy { it.id }.toList()
    }

    fun addTheme(themeTitle: String, userId: Long): Int? {
        val generatedKey = themesDatabase.insertAndGenerateKey(Themes) {
            set(Themes.title, themeTitle)
            set(Themes.userId, userId)
        } as? Int
        return generatedKey
    }

    fun removeThemeById(id: Int, userId: Long): Boolean {
        val deletedRows = themesDatabase.delete(Themes) {
            (it.userId eq userId) and (it.id eq id)
        }
        return deletedRows > 0
    }

    fun clearAllThemes(userId: Long): Boolean {
        val deletedRows = themesDatabase.delete(Themes) {
            it.userId eq userId
        }
        return deletedRows > 0
    }

    fun getRandomTheme(userId: Long): ThemeEntity? {
        val userThemes = themesDatabase.sequenceOf(Themes).filter { it.userId eq userId }.toList()
        val randomTheme = userThemes.randomOrNull()
        if (randomTheme != null) themesDatabase.delete(Themes) { (it.userId eq userId) and (it.id eq randomTheme.id) }
        return randomTheme
    }

}