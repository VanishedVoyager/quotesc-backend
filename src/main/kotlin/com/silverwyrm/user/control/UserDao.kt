package com.silverwyrm.user.control

import com.silverwyrm.user.entity.QuoteUser
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@ApplicationScoped
open class UserDao {
    @Inject
    open lateinit var em: EntityManager

    open fun findAll(): List<QuoteUser>{
        val e = em.createNamedQuery("quoteUser.findAll", QuoteUser::class.java)
        return e.resultList
    }

    @Transactional
    open fun add(user: QuoteUser): QuoteUser {
        em.persist(user)
        return user
    }

    @Transactional
    open fun update(user: QuoteUser): QuoteUser {
        return em.merge(user)
    }

    @Transactional
    open fun delete(taskId: Long): QuoteUser {
        val toRemove = em.find(QuoteUser::class.java, taskId)
        em.remove(toRemove)
        return toRemove
    }
}