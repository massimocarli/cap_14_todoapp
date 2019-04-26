package uk.co.massimocarli.todoapp.todolist

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import uk.co.massimocarli.todoapp.NewToDoFragment
import uk.co.massimocarli.todoapp.R
import uk.co.massimocarli.todoapp.db.DbProvider
import uk.co.massimocarli.todoapp.db.ToDo
import uk.co.massimocarli.todoapp.db.ToDoDatabase
import uk.co.massimocarli.todoapp.detail.ShowToDoFragment
import uk.co.massimocarli.todoapp.nav.Navigation
import uk.co.massimocarli.todoapp.util.OnSelectedItemListener


/**
 * A placeholder fragment containing a simple view.
 */
class ToDoListFragment : Fragment() {

    lateinit var navigation: Navigation
    lateinit var dbProvider: DbProvider
    lateinit var adapter: ToDoAdapter
    val model: MutableList<ToDo> = mutableListOf()

    companion object {
        fun newInstance() = ToDoListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigation = activity as Navigation
        dbProvider = activity as DbProvider
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { view ->
            navigation.showFragmentWithBackStack(NewToDoFragment.newInstance(), "NEW_TODO_TAG")
        }
        adapter = ToDoAdapter(model, object : OnSelectedItemListener<ToDo> {
            override fun onSelected(item: ToDo, isLongClick: Boolean) {
                val frag: Fragment
                if (isLongClick) {
                    frag = NewToDoFragment.newInstance(item.id)
                } else {
                    frag = ShowToDoFragment.newInstance(item.id)
                }
                navigation.showFragmentWithBackStack(frag, "SHOW_TODO")
            }
        })
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            context,
            layoutManager.getOrientation()
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        LoadToDoAsyncTask(dbProvider.getToDoDatabase()) {
            updateToDo(it)
        }.execute()
    }

    private fun updateToDo(newModel: List<ToDo>) {
        val diffCallback = ToDoDiff(model, newModel)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        model.clear()
        model.addAll(newModel)
        diffResult.dispatchUpdatesTo(adapter)
        recyclerView.scrollToPosition(0)
    }

    class LoadToDoAsyncTask(val db: ToDoDatabase, val callback: (List<ToDo>) -> Unit) :
        AsyncTask<Void, Void, List<ToDo>>() {
        override fun doInBackground(vararg params: Void?): List<ToDo> = db.getToDoDao().findAllAsList()

        override fun onPostExecute(result: List<ToDo>?) {
            super.onPostExecute(result)
            result?.run {
                callback(this)
            }
        }
    }
}
