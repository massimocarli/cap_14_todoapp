package uk.co.massimocarli.todoapp.todolist

import androidx.recyclerview.widget.DiffUtil
import uk.co.massimocarli.todoapp.db.ToDo

class ToDoDiff(val before: List<ToDo>, val after: List<ToDo>) : DiffUtil.Callback() {

    // They are the same if they have the same Id, name and description
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val before = before[oldItemPosition]
        val after = after[newItemPosition]
        return before.id == after.id && before.name == after.name && before.description == after.description
    }

    override fun getOldListSize(): Int = before.size

    override fun getNewListSize(): Int = after.size

    // They are the same if they have the same Id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areItemsTheSame(oldItemPosition, newItemPosition)

}