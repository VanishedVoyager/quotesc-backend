package com.silverwyrm.user.entity

import com.silverwyrm.person.entity.Person
import com.silverwyrm.review.entity.Review
import javax.persistence.*

@Entity
@NamedQuery(name = "quoteUser.findAll", query = "select u from QuoteUser u")
open class QuoteUser(id: Long? = null) : Person(id) {
    open lateinit var subject: String

    @OneToMany(mappedBy = "user")
    open lateinit var reviews: List<Review>
}