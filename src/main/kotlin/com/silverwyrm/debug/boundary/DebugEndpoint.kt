package com.silverwyrm.debug.boundary

import com.silverwyrm.debug.control.DebugGenerator
import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.person.entity.Person
import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import javax.inject.Inject
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/debug")
open class DebugEndpoint {

    @Inject
    lateinit var quoteDao: QuoteDao

    @Inject
    lateinit var debugGenerator: DebugGenerator

    @POST
    @Path("/generate")
    fun genTestData(): Response {
        debugGenerator.generateSomeData()
        return Response.ok("${quoteDao.count()} Quotes now in DB").build()
    }
}