package com.silverwyrm.graphql

import com.silverwyrm.debug.boundary.JwtDataFetchers
import com.silverwyrm.group.boundary.GroupDataFetchers
import com.silverwyrm.person.boundary.PersonDataFetchers
import com.silverwyrm.quote.boundary.QuoteDataFetchers
import com.silverwyrm.quote.boundary.QuoteDataMutator
import com.silverwyrm.review.boundary.ReviewDataFetchers
import com.silverwyrm.statistics.boundary.StatisticsDataFetchers
import com.silverwyrm.tag.boundary.TagDataFetchers
import com.silverwyrm.user.boundary.UserDataFetchers
import com.silverwyrm.user.boundary.UserDataMutators
import graphql.schema.DataFetcher
import graphql.schema.idl.RuntimeWiring
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject

@ApplicationScoped
open class WiringFactory {

    @Inject
    lateinit var userDataFetchers: UserDataFetchers
    @Inject
    lateinit var userDataMutators: UserDataMutators
    @Inject
    lateinit var groupDataFetchers: GroupDataFetchers
    @Inject
    lateinit var personDataFetchers: PersonDataFetchers
    @Inject
    lateinit var quoteDataFetchers: QuoteDataFetchers
    @Inject
    lateinit var quoteDataMutator: QuoteDataMutator
    @Inject
    lateinit var reviewDataFetchers: ReviewDataFetchers
    @Inject
    lateinit var statisticsDataFetchers: StatisticsDataFetchers
    @Inject
    lateinit var tagDataFetchers: TagDataFetchers
    @Inject
    lateinit var jwtDataFetchers: JwtDataFetchers

    @Produces
    fun runtimeWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query") {
                    it.generateWirings(userDataFetchers)
                    it.generateWirings(groupDataFetchers)
                    it.generateWirings(quoteDataFetchers)
                    it.generateWirings(personDataFetchers)
                    it.generateWirings(reviewDataFetchers)
                    it.generateWirings(statisticsDataFetchers)
                    it.generateWirings(tagDataFetchers)
                    it.generateWirings(jwtDataFetchers)
                }
                .type("Mutation"){
                    it.generateWirings(userDataMutators)
                    it.dataFetcher("quote") { Empty() }
                }
                .type("QuoteMutator"){
                    it.generateWirings(quoteDataMutator)
                }
                .build()
    }
}

class Empty