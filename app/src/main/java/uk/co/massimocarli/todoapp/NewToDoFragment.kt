package uk.co.massimocarli.todoapp

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_new_todo.*
import uk.co.massimocarli.todoapp.db.DbProvider
import uk.co.massimocarli.todoapp.db.ToDo
import uk.co.massimocarli.todoapp.db.ToDoDatabase
import uk.co.massimocarli.todoapp.detail.ShowToDoFragment
import uk.co.massimocarli.todoapp.nav.Navigation
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class NewToDoFragment : Fragment() {

    lateinit var dbProvider: DbProvider
    lateinit var navigation: Navigation
    var currentId: Int = 0

    companion object {
        const val ID_KEY = "TODO_ID"
        fun newInstance(id: Int = 0): NewToDoFragment {
            val frag = NewToDoFragment()
            val args = Bundle()
            args.putInt(ID_KEY, id)
            frag.arguments = args
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_todo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbProvider = activity as DbProvider
        navigation = activity as Navigation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createButton.setOnClickListener {
            val name = nameEditText.text?.toString()
            if (name != null) {
                val description = descriptionEditText.text?.toString()
                val newItem = ToDo(name, description, Date())
                newItem.id = currentId
                if (currentId == 0) {
                    SaveAsync(dbProvider.getToDoDatabase(), navigation).execute(newItem)
                } else {
                    UpdateAsync(dbProvider.getToDoDatabase(), navigation).execute(newItem)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ShowToDoFragment.GetToDoAsync(dbProvider.getToDoDatabase()) {
            currentId = it?.id ?: 0
            nameEditText.setText(it?.name, TextView.BufferType.EDITABLE)
            descriptionEditText.setText(it?.description, TextView.BufferType.EDITABLE)
        }.execute(arguments?.getInt(ShowToDoFragment.ID_KEY))
    }

    class SaveAsync(val todoDb: ToDoDatabase, val nav: Navigation) : AsyncTask<ToDo, Void, Long>() {
        override fun doInBackground(vararg params: ToDo): Long = todoDb.getToDoDao().createToDo(params[0])

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            nav.back()
        }
    }

    class UpdateAsync(val todoDb: ToDoDatabase, val nav: Navigation) : AsyncTask<ToDo, Void, Int>() {
        override fun doInBackground(vararg params: ToDo): Int = todoDb.getToDoDao().updateToDo(params[0])

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            nav.back()
        }
    }
}
