package uk.co.massimocarli.todoapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.massimocarli.todoapp.db.ToDo
import uk.co.massimocarli.todoapp.db.ToDoDAO
import uk.co.massimocarli.todoapp.db.ToDoDatabase
import java.io.IOException
import java.util.*

/**
 * This is the class in order to test the CRUD operation in ToDo Database
 */
@RunWith(AndroidJUnit4::class)
class ToDoCrudTest {

    private lateinit var todoDao: ToDoDAO
    private lateinit var db: ToDoDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ToDoDatabase::class.java
        ).build()
        todoDao = db.getToDoDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun createNewToDo() {
        val newToDo = ToDo("name", "description", Date())
        todoDao.createToDo(newToDo)
        val result = todoDao.findAll()
        assertThat(result.get(0), equalTo(newToDo))
    }

    @Test
    fun updateToDoName() {
        // We create a Todo and verify it's into the DB
        val newToDo = ToDo("name", "description", Date())
        todoDao.createToDo(newToDo)
        val result = todoDao.findAll()
        val insertedToDo = result.get(0)
        assertThat(insertedToDo, equalTo(newToDo))
        // We update the todo name
        val updatedTodo = insertedToDo.copy(name = "new_name")
        updatedTodo.id = insertedToDo.id
        todoDao.updateToDo(updatedTodo)
        // We read the new one
        val updatedResult = todoDao.findAll()
        // Check it's been updated
        assertThat(updatedResult.get(0), equalTo(updatedTodo))
        // We also check there's only 1 item
        assertThat(result.size, equalTo(1))
    }

    @Test
    fun deleteToDo() {
        // We create a Todo and verify it's into the DB
        val newToDo = ToDo("name", "description", Date())
        todoDao.createToDo(newToDo)
        val result = todoDao.findAll()
        assertThat(result.size, equalTo(1))
        val insertedToDo = result.get(0)
        assertThat(insertedToDo, equalTo(newToDo))
        // We delete the todo
        todoDao.deleteToDo(insertedToDo)
        val newResult = todoDao.findAll()
        // DB is now empty
        assertThat(newResult.size, equalTo(0))
    }

    @Test
    fun queryToDoByName() {
        // We create a Todo and verify it's into the DB
        val newToDo = ToDo("name", "description", Date())
        todoDao.createToDo(newToDo)
        val result = todoDao.findByName("name")
        val insertedToDo = result.get(0)
        assertThat(insertedToDo, equalTo(newToDo))
    }
}
