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

    @Transactional
    open fun add(Quote: Quote): Quote {
        em.persist(Quote)
        return Quote
    }

    @Transactional
    open fun update(Quote: Quote): Quote {
        return em.merge(Quote)
    }

    @Transactional
    open fun delete(taskId: Long): Quote {
        val toRemove = em.find(Quote::class.java, taskId)
        em.remove(toRemove)
        return toRemove
    }
}