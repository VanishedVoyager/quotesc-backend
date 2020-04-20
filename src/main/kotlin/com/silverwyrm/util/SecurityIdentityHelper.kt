package com.silverwyrm.util

import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal
import javax.ws.rs.NotAuthorizedException

val SecurityIdentity.jwtPrincipal: DefaultJWTCallerPrincipal
    get() = this.principal as? DefaultJWTCallerPrincipal ?: throw NotAuthorizedException("Not Authenticated with valid JWT.")