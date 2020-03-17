package com.silverwyrm.graphql

import graphql.schema.DataFetcher
import graphql.schema.idl.TypeRuntimeWiring
import javax.enterprise.context.ApplicationScoped
import javax.xml.crypto.Data
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.*

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class GraphQLField(val name: String)

//@Retention(AnnotationRetention.RUNTIME)
//@Target(AnnotationTarget.CLASS)
//annotation class GraphQlDataFetcher(vararg val presentOnTypes: String)

fun TypeRuntimeWiring.Builder.generateWirings(obj: Any): TypeRuntimeWiring.Builder {
    println("Generate Wirings for ${obj::class.simpleName}")

    obj::class.memberProperties.filter {
        it.returnType.isSubtypeOf(DataFetcher::class.createType(listOf(KTypeProjection.STAR)))
    }.forEach {
        val name = it.findAnnotation<GraphQLField>()?.name ?: it.name

        val fetcher = it.call(obj) as DataFetcher<*>
        this.dataFetcher(name, fetcher)
        println("Wiring: $name")
    }
    return this
}
//
//fun RuntimeWiring.Builder.applyAnnotations(): RuntimeWiring.Builder {
//
//}