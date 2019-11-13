package com.silverwyrm.quote.control

import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@ApplicationScoped
open class QuoteDao: PanacheRepository<Quote>, AdvancedPanacheRepository<Quote>{
    open fun findWithPerson(personId: Long): List<Quote> {
        return find("person.id", personId).list()
    }
}