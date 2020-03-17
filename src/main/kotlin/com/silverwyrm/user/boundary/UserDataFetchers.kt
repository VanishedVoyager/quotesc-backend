package com.silverwyrm.user.boundary

import com.silverwyrm.graphql.GraphQLField
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import graphql.schema.DataFetcher
import io.quarkus.security.identity.SecurityIdentity
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class UserDataFetchers {

    @Inject
    lateinit var userDao: UserDao

    @GraphQLField("ping")
    val ping = DataFetcher<String> {
        "Yeet"
    }

    @GraphQLField("users")
    val users = DataFetcher {
        userDao.findAll().list<QuoteUser>()
    }

}