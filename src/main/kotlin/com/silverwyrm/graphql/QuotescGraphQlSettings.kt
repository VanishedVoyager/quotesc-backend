package com.silverwyrm.graphql

import at.obyoxar.graphql.GraphQLSettings
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.FileNotFoundException
import java.net.URL
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class QuotescGraphQlSettings : GraphQLSettings {
    @ConfigProperty(name = "graphql.schema.path")
    lateinit var schemaPath: String

    override val schemaFile: URL
        get() = javaClass.getResource("/${schemaPath}")
                ?: throw FileNotFoundException("Couldn't load /${schemaPath} from classPath")

}