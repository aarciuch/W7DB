package psm.lab.w7db.DI

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import psm.lab.w7db.DB.DbVm
import psm.lab.w7db.DB.PersonDB
import psm.lab.w7db.DB.PersonRepository

val appModules = module {
    //DB
    single {Room.databaseBuilder(get(), PersonDB::class.java, "person_db")
        .build()
    }

    //DAO
    single { get<PersonDB>().getPersonDao() }

    //Repository z single
    single { PersonRepository(get(), androidContext()) }

    //Repository z singleOf bo ma prosty konstruktor ze znanymi parametrami
    //singleOf(::PersonRepository)
    viewModelOf(::DbVm)
}