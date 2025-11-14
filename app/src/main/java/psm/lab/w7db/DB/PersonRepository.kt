package psm.lab.w7db.DB

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.File

class PersonRepository(private val dao: PersonDao, private val context: Context) {

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
        context.contentResolver.openOutputStream(uri)?.use { output ->
            Log.i("FILE2", line)
            output.write(line.toByteArray())
        }
    }

    fun readFromFilePublic(uri: Uri) : List<String>{
        var content : List<String> = emptyList()
        context.contentResolver.openInputStream(uri)?.use { input ->
            content = input.bufferedReader().readLines()
        }
        return content
    }

    fun writeImageToUri(uri: Uri, bitmap: Bitmap) {
        context.contentResolver.openOutputStream(uri)?.use { output ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
        }
    }

    fun readImageFromUri(uri: Uri): Bitmap? {
        return context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    }


}