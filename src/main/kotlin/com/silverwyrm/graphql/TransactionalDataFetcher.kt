package com.silverwyrm.graphql

import com.silverwyrm.util.asListOfType
import com.silverwyrm.util.asMapOfType
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

typealias GraphQlObject = LinkedHashMap<String, Any>



@SuppressWarnings("UNCHECKED_CAST")
fun LinkedHashMap<String, Any>.getObject(key: String): LinkedHashMap<String, Any> {
    return (this[key] as? LinkedHashMap<*, *>)?.asMapOfType() ?: throw RuntimeException("Tried to read LinkedHashMap<String, Any> out of LinkedHashMap<String, Any> which couldn't be casted.")
}

@SuppressWarnings("UNCHECKED_CAST")
fun LinkedHashMap<String, Any>.getObjectList(key: String): List<LinkedHashMap<String, Any>> {
    return (this[key] as? List<*>)?.asListOfType() ?: throw RuntimeException("Tried to read List<LinkedHashMap<String, Any>> out of LinkedHashMap<String, Any> which couldn't be casted.")
}

fun LinkedHashMap<String, Any>.getInt(key: String): Int {
    return this[key] as? Int ?: throw RuntimeException("Tried to read Int out of LinkedHashMap<String, Any> which couldn't be casted.")
}
fun LinkedHashMap<String, Any>.getString(key: String): String {
    return this[key] as? String ?: throw RuntimeException("Tried to read String out of LinkedHashMap<String, Any> which couldn't be casted.")
}