package uk.co.massimocarli.todoapp

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.massimocarli.todoapp.db.MIGRATION_2_TO_3
import uk.co.massimocarli.todoapp.db.ToDoDatabase
import java.io.IOException

/**
 * Example of testing for migration
 */
@RunWith(AndroidJUnit4::class)
class ToDoMigrationTest {
    private val TEST_DB = "todo-db"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        ToDoDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun testMigration2To3() {
        // We verify that version 1 is working
        var db = helper.createDatabase(TEST_DB, 2).apply {
            // HERE you can insert some data for testing
            close()
        }

        // We verify that data are ok with version 3
        db = helper.runMigrationsAndValidate(TEST_DB, 3, true, MIGRATION_2_TO_3)
        // HERE you can use the DB in order to valide the data in the new version of the schema
    }
}
