package com.silverwyrm.user.entity

import com.silverwyrm.person.entity.Person
import com.silverwyrm.review.entity.Review
import javax.persistence.*

@Entity
class QuoteUser(
        @Id
        @Column(name = "id")
        var id: Long? = null
)  {
    lateinit var subject: String

    @OneToMany(mappedBy = "user")
    lateinit var reviews: List<Review>

    @OneToOne
    @PrimaryKeyJoinColumn(name = "id")
    lateinit var person: Person
}