package com.silverwyrm.quote.control

import com.silverwyrm.quote.entity.Quote
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@ApplicationScoped
open class QuoteDao {

    @Inject
    open lateinit var em: EntityManager

    open fun findAll(): List<Quote>{
        val e = em.createNamedQuery("quote.findAll", Quote::class.java)
        return e.resultList
    }

    open fun count(): Int {
        val e = em.createNamedQuery("quote.count", Int::class.java)
        return e.singleResult
    }

    @Transactional
    open fun add(quote: Quote): Quote {
        em.persist(quote)
        em.flush()
        em.refresh(quote)
        return quote
    }

    @Transactional
    open fun update(quote: Quote): Quote {
        return em.merge(quote)
    }

    @Transactional
    open fun delete(taskId: Long): Quote {
        val toRemove = em.find(Quote::class.java, taskId)
        em.remove(toRemove)
        return toRemove
    }

    fun findWithPerson(personId: Long): List<Quote> {
        val q = em.createNamedQuery("quote.findWithPersonId", Quote::class.java)
        q.setParameter("personId", personId)
        return q.resultList
    }
}