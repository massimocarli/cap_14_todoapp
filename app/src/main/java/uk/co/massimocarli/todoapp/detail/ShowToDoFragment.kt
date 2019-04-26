package uk.co.massimocarli.todoapp.detail

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_show_todo.*
import uk.co.massimocarli.todoapp.R
import uk.co.massimocarli.todoapp.db.DbProvider
import uk.co.massimocarli.todoapp.db.ToDo
import uk.co.massimocarli.todoapp.db.ToDoDatabase
import uk.co.massimocarli.todoapp.nav.Navigation


/**
 * A placeholder fragment containing a simple view.
 */
class ShowToDoFragment : Fragment() {

    lateinit var dbProvider: DbProvider
    lateinit var navigation: Navigation

    companion object {
        const val ID_KEY = "TODO_ID"
        fun newInstance(id: Int = 0): ShowToDoFragment {
            val frag = ShowToDoFragment()
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
        return inflater.inflate(R.layout.fragment_show_todo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbProvider = activity as DbProvider
        navigation = activity as Navigation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteButton.setOnClickListener {
            DeleteAsync(dbProvider.getToDoDatabase(), navigation).execute(arguments?.getInt(ID_KEY))
        }
    }

    override fun onStart() {
        super.onStart()
        GetToDoAsync(dbProvider.getToDoDatabase()) {
            nameText.text = it?.name
            descriptionText.text = it?.description
        }.execute(arguments?.getInt(ID_KEY))
    }

    class GetToDoAsync(val todoDb: ToDoDatabase, val callback: (ToDo?) -> Unit) : AsyncTask<Int, Void, ToDo?>() {
        override fun doInBackground(vararg params: Int?): ToDo? =
            todoDb.getToDoDao().findById(params[0] ?: 0)

        override fun onPostExecute(result: ToDo?) {
            super.onPostExecute(result)
            result?.run {
                callback(this)
            }
        }

    }

    class DeleteAsync(val todoDb: ToDoDatabase, val nav: Navigation) : AsyncTask<Int, Void, Int>() {
        override fun doInBackground(vararg params: Int?): Int = todoDb.getToDoDao().deleteToDoById(params[0] ?: 0)

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            nav.back()
        }
    }
}
