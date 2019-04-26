package uk.co.massimocarli.todoapp.db

import androidx.room.TypeConverter
import java.util.*

/**
 * This contains TypeConverter annotated methods
 */
class CustomConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}