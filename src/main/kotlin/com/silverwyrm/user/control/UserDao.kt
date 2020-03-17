package com.silverwyrm.user.control

import com.silverwyrm.user.entity.QuoteUser
import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.quarkus.security.Authenticated
import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@ApplicationScoped
class UserDao : PanacheRepositoryBase<QuoteUser, String> {
    @Inject
    lateinit var securityIdentity: SecurityIdentity

    @Authenticated
    fun getMe(): QuoteUser {
        val jwt = securityIdentity.principal as DefaultJWTCallerPrincipal
//        return findById(jwt.subject) TODO Why doesn't this work?
        return find("subject", jwt.subject).singleResult()
    }
}