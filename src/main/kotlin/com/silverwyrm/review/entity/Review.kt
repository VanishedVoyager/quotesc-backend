package com.silverwyrm.review.entity

import com.silverwyrm.person.entity.Person
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.user.entity.QuoteUser
import org.hibernate.annotations.Check
import javax.persistence.*
import kotlin.math.max
import kotlin.math.min

@Entity
@Check(constraints = "score BETWEEN -5 AND 5")
open class Review(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {

    @Column(name = "score")
    var score: Int = 0
        set(value) {
            field = min(5, max(value, -5))
        }

    var votes: Int = 0


    @ManyToOne
    lateinit var user: QuoteUser

    @ManyToOne
    lateinit var quote: Quote

    fun upvote(){
        score += 1
        votes++
    }

    fun downvote(){
        score -= 1
        votes ++
    }
}