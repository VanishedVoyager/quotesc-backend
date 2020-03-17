package com.silverwyrm.user.boundary

import graphql.schema.DataFetcher
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserDataFetchers {
    val users = DataFetcher<String> {
        "Yeet"
    }
}