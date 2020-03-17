package com.silverwyrm.graphql

import com.silverwyrm.util.transaction
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import javax.transaction.Transactional

class TransactionalDataFetcher<T>(val action: (environment: DataFetchingEnvironment) -> T): DataFetcher<T> {
    override fun get(environment: DataFetchingEnvironment): T{
        return transaction {
            action(environment)
        }
    }
}