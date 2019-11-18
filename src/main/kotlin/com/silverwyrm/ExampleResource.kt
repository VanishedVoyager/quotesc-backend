package com.silverwyrm

import io.quarkus.security.Authenticated
import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/hello")
@Authenticated
class ExampleResource {

    @Inject
    private lateinit var identity: SecurityIdentity

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hello(): Response {
        val principal = identity.principal as JWTCallerPrincipal
        return Response.ok(principal).build()
    }
}