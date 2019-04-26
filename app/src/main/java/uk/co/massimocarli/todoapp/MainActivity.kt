package uk.co.massimocarli.todoapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.InvalidationTracker
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.massimocarli.todoapp.db.DbProvider
import uk.co.massimocarli.todoapp.db.MIGRATION_2_TO_3
import uk.co.massimocarli.todoapp.db.MIGRATION_3_TO_4
import uk.co.massimocarli.todoapp.db.ToDoDatabase
import uk.co.massimocarli.todoapp.nav.Navigation
import uk.co.massimocarli.todoapp.nav.NavigationImpl
import uk.co.massimocarli.todoapp.todolist.ToDoListFragment


class MainActivity : AppCompatActivity(), Navigation, DbProvider {

    override fun back() = navigation.back()

    override fun getToDoDatabase(): ToDoDatabase = db

    override fun showFragment(frag: Fragment) {
        navigation.showFragment(frag)
    }

    override fun showFragmentWithBackStack(frag: Fragment, backStackTag: String?) {
        navigation.showFragmentWithBackStack(frag, backStackTag)
    }

    lateinit var navigation: Navigation

    lateinit var db: ToDoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigation = NavigationImpl(this)
        db = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            "todo-db"
        )
            //.allowMainThreadQueries()
            //.setQueryExecutor(Executors.newFixedThreadPool(10))
            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_2_TO_3, MIGRATION_3_TO_4)
            .build()
        if (savedInstanceState == null) {
            navigation.showFragment(ToDoListFragment.newInstance())
        }
        db.invalidationTracker.addObserver(object : InvalidationTracker.Observer("todo") {
            override fun onInvalidated(tables: MutableSet<String>) {
                Log.i("INVALIDATION_TRACKER", "Table sSet: $tables")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
