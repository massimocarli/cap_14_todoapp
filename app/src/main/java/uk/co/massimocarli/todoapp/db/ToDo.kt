package uk.co.massimocarli.todoapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * This is the entity that defines a ToDo.
 */
@Entity
data class ToDo(
    val name: String,
    val description: String?,
    val dueDate: Date?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


@Entity
data class Dummy(@PrimaryKey val id: Int)

data class ToDoPojo(
    val name: String,
    val description: String?
)