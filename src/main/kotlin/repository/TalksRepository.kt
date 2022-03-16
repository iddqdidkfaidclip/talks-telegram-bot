package repository

import repository.database.DatabaseManager
import repository.database.entity.ThemeEntity

object TalksRepository {
    // Database
    private val db by lazy {
        DatabaseManager()
    }

    fun addTheme(theme: String, userId: Long): Int? {
        return db.addTheme(theme, userId)
    }

    fun getAllThemes(userId: Long): List<ThemeEntity> {
        return db.getAllThemes(userId)
    }

    fun removeThemeById(themeId: Int, userId: Long): Boolean {
        return db.removeThemeById(themeId, userId)
    }

    fun clearAllThemes(userId: Long): Boolean {
        return db.clearAllThemes(userId)
    }

    fun getRandomTheme(userId: Long): ThemeEntity? {
        return db.getRandomTheme(userId)
    }
}