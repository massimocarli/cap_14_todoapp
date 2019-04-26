package uk.co.massimocarli.todoapp.db

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface ToDoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createToDo(todo: ToDo): Long

    @Insert
    fun createToDoDouble(todo1: ToDo, todo2: ToDo)

    @Insert
    fun createMultiToDo(vararg todo: ToDo)

    @Insert
    fun createListToDo(todos: List<ToDo>): Array<Long>

    @Query("SELECT * FROM todo")
    fun findAll(): Array<ToDo>

    @Query("SELECT * FROM todo")
    fun findAllAsList(): List<ToDo>

    @Query("SELECT * FROM todo")
    fun findNamesAsList(): List<ToDoPojo>

    @Query("SELECT * FROM todo where id = :id")
    fun findById(id: Int): ToDo?

    @Query("SELECT * FROM todo where id in (:ids)")
    fun findById(ids: List<Int>): List<ToDo>

    @Query("SELECT * FROM todo")
    fun findAllAsCursor(): Cursor

    @Query("SELECT * FROM todo WHERE name = :name")
    fun findByName(name: String): List<ToDo>

    @Delete
    fun deleteToDo(todo: ToDo)

    @Delete
    fun deleteToDoDouble(todo1: ToDo, todo2: ToDo)

    @Delete
    fun deleteMultiToDo(vararg todo: ToDo)

    @Delete
    fun deleteListToDo(todos: List<ToDo>)

    @Query("DELETE FROM todo WHERE id = :id")
    fun deleteToDoById(id: Int): Int

    @Query("DELETE FROM todo WHERE id IN (:ids)")
    fun deleteToDoById(ids: List<Int>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateToDo(todo: ToDo): Int

    @Update
    fun updateToDoDouble(todo1: ToDo, todo2: ToDo)

    @Update
    fun updateMultiToDo(vararg todo: ToDo)

    @Update
    fun updateListToDo(todos: List<ToDo>): Int

    @RawQuery
    fun deleteToDoById(query: SupportSQLiteQuery): Int

    @Transaction
    fun replaceToDo(newTodo: ToDo, oldTodo: ToDo) {
        deleteToDo(oldTodo)
        createToDo(newTodo)
    }
}
