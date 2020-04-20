package com.silverwyrm.quote.entity

import com.silverwyrm.nickname.entity.Nickname
import com.silverwyrm.person.entity.Person
import com.silverwyrm.quoteperson.entity.QuotePerson
import com.silverwyrm.review.entity.Review
import com.silverwyrm.tag.entity.Tag
import com.silverwyrm.user.entity.QuoteUser
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
open class Quote(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    public lateinit var text: String

//    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.EAGER, targetEntity = Person::class)
//    lateinit var quotedPersons: List<Person>

    @OneToMany(mappedBy = "quote", targetEntity = QuotePerson::class)
    lateinit var quotePersons: List<QuotePerson>

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    lateinit var quoter: QuoteUser

    @JvmSuppressWildcards
    @ManyToMany(targetEntity = Tag::class)
    lateinit var tags: List<Tag>

    @OneToMany(mappedBy = "quote", targetEntity = Review::class)
    lateinit var reviews: List<Review>

    lateinit var date: LocalDateTime

    var brain: Int = 0

//    @delegate:Transient
//    val persons: List<Person> by lazy { quotePersons.map { it.person } }
}