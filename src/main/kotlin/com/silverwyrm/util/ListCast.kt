package com.silverwyrm.util

inline fun <reified T> List<*>.asListOfType(): List<T>? =
        if (all { it is T })
            @Suppress("UNCHECKED_CAST")
            this as List<T> else
            null

inline fun <reified K, reified V> LinkedHashMap<*, *>.asMapOfType(): LinkedHashMap<K, V>? =
        if (all { it.key is K && it.value is V })
            @Suppress("UNCHECKED_CAST")
            this as LinkedHashMap<K, V> else
            null