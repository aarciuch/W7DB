package psm.lab.w7db.DB

import kotlinx.coroutines.flow.Flow

class PersonRepository(private val dao: PersonDao) {
    fun getPersons() : Flow<List<Person>> = dao.getAllPersons()
    suspend fun addPerson(person: Person) = dao.insert(person = person)
    suspend fun removePerson(person: Person) = dao.delete(person = person)
    suspend fun updatePerson(person: Person) = dao.update(person = person)
    fun getCount() : Flow<Int> = dao.getCount()
}