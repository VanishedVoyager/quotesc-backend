package com.silverwyrm.user.boundary

import com.silverwyrm.person.entity.Person
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

////@Path("/users")
//@Produces(MediaType.APPLICATION_JSON)
//open class UserEndpoint {
//
//    @Inject
//    open lateinit var userDao: UserDao
//
//    @GET
//    fun findAll(): List<QuoteUser>{
//        return userDao.findAll()
//    }
//
//    @POST
//    fun create(subject: QuoteUser): QuoteUser {
//        return userDao.add(subject)
//    }
//}