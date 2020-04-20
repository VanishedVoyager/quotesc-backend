package com.silverwyrm.person.entity

import com.silverwyrm.group.entity.Group
import com.silverwyrm.nickname.entity.Nickname
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.quoteperson.entity.QuotePerson
import com.silverwyrm.user.entity.QuoteUser
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Person(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    lateinit var firstName: String
    lateinit var lastName: String

    @OneToMany(mappedBy = "person", targetEntity = QuotePerson::class)
    lateinit var quotePersons: List<QuotePerson>

    @JvmSuppressWildcards
    @ManyToMany(targetEntity = Group::class)
    lateinit var groups: List<Group>

    @OneToOne(mappedBy = "person")
    var user: QuoteUser? = null

//
//    @delegate:Transient
//    val nicknames: List<Nickname> by lazy { quotePersons.map { it.nickname } }

//    @delegate:Transient
//    val quotes: List<Quote> by lazy { quotePersons.map { it.quote } }

//    @JsonIgnoreProperties("person")
//    @OneToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.EAGER, mappedBy = "person")
//    open lateinit var nicknames: List<Nickname>
}