package com.silverwyrm.quote.entity

import com.silverwyrm.person.entity.Person
import com.silverwyrm.user.entity.QuoteUser
import javax.persistence.*

@Entity
class Quote(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    public lateinit var text: String

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.EAGER, targetEntity = Person::class)
    lateinit var quotedPersons: List<Person>

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    lateinit var quoter: QuoteUser
}