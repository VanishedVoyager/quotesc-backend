package com.silverwyrm.quote.boundary

import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import io.quarkus.hibernate.orm.panache.Panache
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/quotes")
@Produces(MediaType.APPLICATION_JSON)
open class QuoteEndpoint {

    @Inject
    open lateinit var quoteDao: QuoteDao

    @GET
    fun findAll(): Response{
        return Response.ok(quoteDao.findAll().list<Quote>()).build()
    }

    @POST
    @Transactional
    fun create(quote: Quote): Response {
        quoteDao.persistAndFlush(quote)
        quoteDao.refresh(quote)
        return Response.ok(quote).build()
    }

    @PUT
    @Transactional
    fun update(quote: Quote): Response {
        quoteDao.merge(quote)
        quoteDao.refresh(quote)
        return Response.ok(quote).build()
    }

    @PUT
    @Transactional
    @Path("{id}")
    fun update(@PathParam("id") id: Long, quote: Quote): Response {
        quote.id = id
        quoteDao.merge(quote)
        quoteDao.refresh(quote)
        return Response.ok(quote).build()
    }

    @DELETE
    fun delete(quote: Quote): Response {
        quoteDao.delete(quote)
        return Response.ok().build()
    }

    @DELETE
    @Path("{id}")
    fun delete(@PathParam("id") id: Long): Response {
        quoteDao.delete("id", id)
        return Response.ok().build()
    }

    @GET
    @Path("/person/{id}")
    fun getByPerson(@PathParam("id") personId: Long): List<Quote> {
        return quoteDao.findWithPerson(personId)
    }
}