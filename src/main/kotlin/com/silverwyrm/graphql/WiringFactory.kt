package com.silverwyrm.graphql

import com.silverwyrm.user.boundary.UserDataFetchers
import graphql.schema.idl.RuntimeWiring
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject

@ApplicationScoped
class WiringFactory {

    @Inject
    lateinit var userDataFetchers: UserDataFetchers

    @Produces
    fun runtimeWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query") {
                    it.dataFetcher("ping", userDataFetchers.users)
                }.build()
    }
}