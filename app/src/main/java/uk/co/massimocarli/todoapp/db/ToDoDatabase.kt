package uk.co.massimocarli.todoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(ToDo::class), version = 4)
@TypeConverters(CustomConverters::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun getToDoDao(): ToDoDAO
}

interface DbProvider {
    fun getToDoDatabase(): ToDoDatabase
}