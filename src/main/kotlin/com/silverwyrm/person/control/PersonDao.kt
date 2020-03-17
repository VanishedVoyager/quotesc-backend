package com.silverwyrm.person.control

import com.silverwyrm.person.entity.Person
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@ApplicationScoped
open class PersonDao: AdvancedPanacheRepository<Person>, PanacheRepository<Person>