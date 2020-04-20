package com.silverwyrm.tag.boundary

import com.silverwyrm.graphql.GraphQLField
import com.silverwyrm.tag.control.TagDao
import com.silverwyrm.tag.entity.Tag
import graphql.schema.DataFetcher
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class TagDataFetchers {

    @Inject
    lateinit var tagsDao: TagDao

    @GraphQLField("tags")
    val tags = DataFetcher {
        tagsDao.findAll().list<Tag>()
    }
}