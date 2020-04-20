package com.silverwyrm.debug.boundary

import com.silverwyrm.debug.entity.JwtDTO
import com.silverwyrm.util.jwtPrincipal
import graphql.schema.DataFetcher
import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class JwtDataFetchers {

    @Inject
    lateinit var securityIdentity: SecurityIdentity

    val pingJwt = DataFetcher {
        val jwt = securityIdentity.jwtPrincipal
        JwtDTO(jwt.subject, jwt.name)
    }
}