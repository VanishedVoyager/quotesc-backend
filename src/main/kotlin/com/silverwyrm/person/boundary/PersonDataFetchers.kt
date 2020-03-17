package com.silverwyrm.person.boundary

import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.person.entity.Person
import graphql.schema.DataFetcher
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class PersonDataFetchers{

    @Inject
    lateinit var personDao: PersonDao

    val persons = DataFetcher {
        personDao.findAll().list<Person>()
    }

    val person = DataFetcher {

    }
}