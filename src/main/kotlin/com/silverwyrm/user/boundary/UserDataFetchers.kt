package com.silverwyrm.user.boundary

import com.silverwyrm.graphql.GraphQLField
import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.person.entity.Person
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import com.silverwyrm.util.transaction
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

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

@ApplicationScoped
class UserDataMutators {

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var personDao: PersonDao

    @Inject
    lateinit var securityIdentity: SecurityIdentity

    @Suppress("ObjectLiteralToLambda")
    val upsertMe = object : DataFetcher<QuoteUser> {
        override fun get(environment: DataFetchingEnvironment?): QuoteUser {
            return transaction {
                val jwt = securityIdentity.principal as DefaultJWTCallerPrincipal
                val user = userDao.find("subject", jwt.subject).singleResultOptional<QuoteUser>()
                if(user.isPresent){

                    user.get()
                }else{
                    val newPerson = Person().apply {
                        firstName = jwt.getClaim("given_name")
                        lastName = jwt.getClaim("family_name")
                    }
                    personDao.persistAndFlush(newPerson)

                    val newUser = QuoteUser().apply {
                        id = newPerson.id
                        subject = jwt.subject
                        person = newPerson
                    }
                    userDao.persistAndFlush(newUser)

                    newUser
                }
            }
        }
    }
}