package com.silverwyrm.nickname.control

import com.silverwyrm.nickname.entity.Nickname
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
open class NicknameDao: PanacheRepository<Nickname> {

    init {
        println("NicknameDao instanciated")
    }

    open fun findByPersonId(personId: Long): List<Nickname> {
        return find("person.id", personId).list()
    }

    open fun test(): String {
        return "I am not mocked"
    }
}