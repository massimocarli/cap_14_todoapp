package uk.co.massimocarli.todoapp.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MIGRATION_2_TO_3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE Dummy")
    }
}

object MIGRATION_3_TO_4 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE ToDo ADD COLUMN dueDate INTEGER")
    }
}
