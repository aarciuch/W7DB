package psm.lab.w7db.DB

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class DbVm(private val repository: PersonRepository, app : Application) : AndroidViewModel(app) {
    val persons : Flow<List<Person>> = repository.getPersons()
    val PersonsCount : Flow<Int> = repository.getCount()
    private var _personsList  = MutableStateFlow<List<String>>(emptyList())
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

    fun saveToFilePrivateArea(fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveToFilePrivate(fileName = fileName)
        }
    }

    fun readFromFilePrivateArea(fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newList = repository.readFromFilePrivate(fileName = fileName)
            _personsList.value = newList
            Log.i("FILE", "${_personsList.value}\n${personList.value}")
        }
    }

    fun  saveToFilePublic(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val persons = repository.getPersons()   // Flow<List<Person>>
            val personList = persons.first()        // suspend
            val line = personList.joinToString("\n") { "${it.id}, ${it.name}" }

            // zapis do wybranego miejsca
            repository.saveToFilePublic(uri, line)
        }
    }
}