package repository.database.entity

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object Themes : Table<ThemeEntity>(tableName = "t_themes") {
    val id = int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val userId = long("user_id").bindTo { it.userId }
}

interface ThemeEntity: Entity<ThemeEntity> {
    companion object : Entity.Factory<ThemeEntity>()

    val id: Int
    val title: String
    val userId: Long
}