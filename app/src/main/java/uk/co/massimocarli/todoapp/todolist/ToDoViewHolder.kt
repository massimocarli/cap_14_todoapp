package uk.co.massimocarli.todoapp.todolist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.todoapp.R
import uk.co.massimocarli.todoapp.db.ToDo
import uk.co.massimocarli.todoapp.util.OnSelectedItemListener

class ToDoViewHolder(view: View, listener: OnSelectedItemListener<ToDo>) : RecyclerView.ViewHolder(view) {

    val nameText: TextView
    val descriptionText: TextView
    lateinit var model: ToDo

    init {
        view.setOnClickListener {
            listener.onSelected(model)
        }
        view.setOnLongClickListener {
            listener.onSelected(model, true)
            true
        }
        nameText = view.findViewById(R.id.todoName)
        descriptionText = view.findViewById(R.id.todoDescription)
    }

    fun bindModel(newModel: ToDo) {
        model = newModel
        nameText.text = model.name
        descriptionText.text = model.description
    }
}