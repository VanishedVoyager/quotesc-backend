package com.silverwyrm.user.entity

import com.silverwyrm.person.entity.Person
import javax.persistence.*

@Entity
@NamedQuery(name = "quoteUser.findAll", query = "select u from QuoteUser u")
open class QuoteUser(id: Long? = null) : Person(id) {
    open lateinit var sub: String
}