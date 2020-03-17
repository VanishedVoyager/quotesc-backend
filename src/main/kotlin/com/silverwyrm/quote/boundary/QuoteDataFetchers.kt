package com.silverwyrm.quote.boundary

import com.silverwyrm.graphql.GraphQLField
import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import graphql.schema.DataFetcher
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class QuoteDataFetchers {
    @Inject
    lateinit var quoteDao: QuoteDao

    @GraphQLField("quotes")
    val quotes = DataFetcher {
        quoteDao.findAll().list<Quote>()
    }
}