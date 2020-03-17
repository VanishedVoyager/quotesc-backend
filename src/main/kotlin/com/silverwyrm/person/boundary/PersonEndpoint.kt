package com.silverwyrm.person.boundary

import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.person.entity.Person
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

//@Path("/persons")
//@Produces(MediaType.APPLICATION_JSON)
//class PersonEndpoint {
//    @Inject
//    lateinit var personDao: PersonDao
//
//    @GET
//    fun findAll(): List<Person>{
//        return personDao.findAll()
//    }
//
//    @POST
//    fun create(subject: Person): Person {
//        return personDao.add(subject)
//    }
//
//    @PUT
//    fun update(subject: Person): Person {
//        return personDao.update(subject)
//    }
//
//    @PUT
//    @Path("{id}")
//    fun update(@PathParam("id") id: Long, subject: Person): Person {
//        subject.id = id
//        return personDao.update(subject)
//    }
//
//    @DELETE
//    fun delete(subject: Person): Person {
//        return personDao.delete(subject.id!!)
//    }
//
//    @DELETE
//    @Path("{id}")
//    fun delete(@PathParam("id") id: Long): Person {
//        return personDao.delete(id)
//    }
//}