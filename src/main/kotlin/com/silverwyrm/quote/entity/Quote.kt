package com.silverwyrm.quote.entity

import com.silverwyrm.person.entity.Person
import com.silverwyrm.user.entity.QuoteUser
import javax.persistence.*

@Entity
@NamedQueries(
NamedQuery(name = "quote.findAll", query = "select q from Quote q"),
NamedQuery(name = "quote.findWithPersonId", query = "select distinct q from Person p, Quote q where q member of p.quotes and p.id = :personId"),
        NamedQuery(name = "quote.count", query = "select count(q) from Quote q")
)
data class Quote(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    public lateinit var text: String

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.EAGER)
    lateinit var quotedPersons: List<Person>

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    lateinit var quoter: QuoteUser
}