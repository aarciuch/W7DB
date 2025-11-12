package psm.lab.w7db.DB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class DbVm(private val repository: PersonRepository, app : Application) : AndroidViewModel(app), KoinComponent {
    val persons : Flow<List<Person>> = repository.getPersons()
    val PersonsCount : Flow<Int> = repository.getCount()

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
}