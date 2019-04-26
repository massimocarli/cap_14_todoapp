package uk.co.massimocarli.todoapp.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.todoapp.R
import uk.co.massimocarli.todoapp.db.ToDo
import uk.co.massimocarli.todoapp.util.OnSelectedItemListener

class ToDoAdapter(val model: List<ToDo>, val listener: OnSelectedItemListener<ToDo>) :
    RecyclerView.Adapter<ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.todo_item_layout, parent, false)
        return ToDoViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = model.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) = holder.bindModel(model[position])
}