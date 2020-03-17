package com.silverwyrm.quote.boundary

import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import io.quarkus.hibernate.orm.panache.Panache
import org.hibernate.PersistentObjectException
import javax.inject.Inject
import javax.persistence.PersistenceContext
import javax.persistence.PersistenceException
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

//@Path("/quotes")
@Produces(MediaType.APPLICATION_JSON)
open class QuoteEndpoint {

    @Inject
    lateinit var quoteDao: QuoteDao

    @GET
    fun findAll(): List<Quote>{
        return quoteDao.findAll().list()
    }

    @POST
    @Transactional
    fun create(quote: Quote): Response {
        try {
            quoteDao.persistAndFlush(quote)
            quoteDao.refresh(quote)
        }catch (ex: PersistenceException){
            if(ex.cause is PersistentObjectException){
                return Response.status(Response.Status.BAD_REQUEST.statusCode, "Posted Quote is already persisted (has an id). Use Put to update").build()
            }
        }
        return Response.ok(quote).build()
    }

    @PUT
    @Transactional
    fun update(quote: Quote): Quote {
        val newQuote = Panache.getEntityManager().merge(quote)
        Panache.getEntityManager().flush()
        quoteDao.refresh(newQuote)
        return newQuote
    }

    @PUT
    @Transactional
    @Path("{id}")
    fun update(@PathParam("id") id: Long, quote: Quote): Quote {
        quote.id = id
        quoteDao.mergeAndFlush(quote)
        quoteDao.refresh(quote)
        return quote
    }

    @DELETE
    @Transactional
    fun delete(quote: Quote): Quote {
        quoteDao.delete(quote)
        return quote
    }

    @DELETE
    @Path("{id}")
    @Transactional
    fun delete(@PathParam("id") id: Long): Quote {
        val quote = quoteDao.findById(id);
        quoteDao.delete(quote)
        return quote
    }

    @GET
    @Path("/person/{id}")
    fun getByPerson(@PathParam("id") personId: Long): List<Quote> {
        return quoteDao.findWithPerson(personId)
    }

    @GET
    @Path("{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val quote = quoteDao.findById(id) ?: return Response.noContent().build()
        return Response.ok(quote).build()
    }
}