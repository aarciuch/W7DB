package psm.lab.w7db.DB

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.File

class PersonRepository(private val dao: PersonDao, private val context: Context) {

    private lateinit var saveFileLauncher: ActivityResultLauncher<String>

    fun getPersons(): Flow<List<Person>> = dao.getAllPersons()
    suspend fun addPerson(person: Person) = dao.insert(person = person)
    suspend fun removePerson(person: Person) = dao.delete(person = person)
    suspend fun updatePerson(person: Person) = dao.update(person = person)
    fun getCount(): Flow<Int> = dao.getCount()

    suspend fun saveToFilePrivate(fileName: String) {
        val persons = getPersons()
        val file = File(context.filesDir, fileName)
        val personList = persons.first()
        val line = personList.joinToString(separator = "\n") { "${it.id}, ${it.name}" }
        Log.i("DB", "${line}")
        Log.i("DB1", "${personList}")
        file.writeText(line)
    }

    fun readFromFilePrivate(fileName: String): List<String> {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) file.readLines() else emptyList()
    }

    fun saveToFilePublic(uri: Uri, line: String) {
        context.contentResolver.openOutputStream(uri)?.use {output ->
            output.write(line.toByteArray())
        }
    }

}