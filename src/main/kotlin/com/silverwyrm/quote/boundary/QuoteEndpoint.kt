package com.silverwyrm.quote.boundary

import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/quotes")
open class QuoteEndpoint {

    @Inject
    lateinit var quoteDao: QuoteDao
//    @Inject
//    lateinit var personDao: PersonDao

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun findAll(): List<Quote>{
        return quoteDao.findAll()
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun create(Quote: Quote): Quote {
        return quoteDao.add(Quote)
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    fun update(quote: Quote): Quote {
        return quoteDao.update(quote)
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun update(@PathParam("id") id: Long, quote: Quote): Quote {
        quote.id = id
        return quoteDao.update(quote)
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    fun delete(quote: Quote): Quote {
        return quoteDao.delete(quote.id!!)
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun delete(@PathParam("id") id: Long): Quote {
        return quoteDao.delete(id)
    }

    @POST
    @Path("/testdata")
    fun genTestData(): Response {
        val quotes = arrayOf(
                Quote().apply { text="Ahhhaahah I bin so BRAIN!N!!" },
                Quote().apply { text="Das Yoinkt den Yettel in den Bettel" }
        )
        quotes.forEach { quoteDao.add(it) }

        return Response.ok("${quotes.size} Persons and ${quotes.size} Quotes added.").build()
    }
}