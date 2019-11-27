package com.silverwyrm.group.entity

import com.silverwyrm.person.entity.Person
import com.silverwyrm.user.entity.QuoteUser
import javax.persistence.*

@Entity
@Table(name = "qgroup")
open class Group(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    open lateinit var name: String

    @JvmSuppressWildcards
    @ManyToMany(mappedBy = "groups", targetEntity = Person::class)
    open lateinit var persons: List<Person>
}