package com.silverwyrm.user.entity

import com.silverwyrm.person.entity.Person
import javax.persistence.*

@Entity
@NamedQuery(name = "quoteUser.findAll", query = "select u from QuoteUser u")
data class QuoteUser(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    lateinit var sub: String

    @OneToOne(cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    var user: Person? = null
}