package com.silverwyrm.user.control

import com.silverwyrm.user.entity.QuoteUser
import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@ApplicationScoped
class UserDao : AdvancedPanacheRepository<QuoteUser>, PanacheRepository<QuoteUser>