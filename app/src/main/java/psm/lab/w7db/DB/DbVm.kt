package psm.lab.w7db.DB

import android.app.Application
import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.io.File

class DbVm(private val repository: PersonRepository, app : Application) : AndroidViewModel(app), KoinComponent {
    val persons : Flow<List<Person>> = repository.getPersons()
    val PersonsCount : Flow<Int> = repository.getCount()
    private var _personsList : MutableStateFlow<List<String>> = MutableStateFlow<List<String>>(emptyList())
    var personList : StateFlow<List<String>> = _personsList

    fun insertPerson(person: Person) {
        viewModelScope.launch {
            repository.addPerson(person = person)
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch {
            repository.removePerson(person = person)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch {
            repository.updatePerson(person = person)
        }
    }

    fun saveToFilePrivate(fileName: String) {
        val file = File(application.filesDir, fileName)
        viewModelScope.launch(Dispatchers.IO) {
            val personList = persons.first()
            val line = personList.joinToString(separator = "\n") {"${it.id}, ${it.name}"}
            Log.i("DB", "${line}")
            Log.i("DB", "${personList}")
            file.writeText(line)
        }
    }

    fun readFromFilePrivate(fileName: String) {
        viewModelScope.launch {
            val file = File(application.filesDir, fileName)
            if (file.exists()) {
                _personsList.value = file.readLines()
            }
        }
    }
}