package com.silverwyrm.util

import io.quarkus.hibernate.orm.panache.Panache
import java.lang.Exception

fun <T> transaction(action: () -> T): T{
    Panache.getTransactionManager().begin()
    try{
        val ret = action()
        Panache.getTransactionManager().commit()
        return ret
    }catch (ex: Exception){
        Panache.getTransactionManager().rollback()
        throw ex
    }
}