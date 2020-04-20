package com.silverwyrm.quoteperson.entity

import com.silverwyrm.nickname.entity.Nickname
import com.silverwyrm.person.entity.Person
import com.silverwyrm.quote.entity.Quote
import java.io.Serializable
import javax.persistence.*

@Entity
@IdClass(QuotePersonId::class)
open class QuotePerson(
) {
    @Id
    @ManyToOne(targetEntity = Quote::class)
    @PrimaryKeyJoinColumn(name="quote_id", referencedColumnName = "id")
    lateinit var quote: Quote

    @Id
    @ManyToOne(targetEntity = Person::class)
    @PrimaryKeyJoinColumn(name="person_id", referencedColumnName = "id")
    lateinit var person: Person

    @OneToOne()
    @JoinColumn(name = "nickname_id")
    lateinit var nickname: Nickname
}

open class QuotePersonId : Serializable {
    open var quote: Long? = null
    open var person: Long? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuotePersonId

        if (quote != other.quote) return false
        if (person != other.person) return false

        return true
    }

    override fun hashCode(): Int {
        var result = quote?.hashCode() ?: 0
        result = 31 * result + (person?.hashCode() ?: 0)
        return result
    }


}