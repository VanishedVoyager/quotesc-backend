package com.silverwyrm.person.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.silverwyrm.nickname.entity.Nickname
import com.silverwyrm.quote.entity.Quote
import org.jboss.resteasy.spi.touri.MappedBy
import javax.persistence.*

@Entity
@NamedQueries(
        NamedQuery(name = "person.findAll", query = "select p from Person p"),
        NamedQuery(name = "person.countQuotes", query = "select p, count(quotes) from Person p group by p") //TODO Doesn't work.
)
@Inheritance(strategy = InheritanceType.JOINED)
open class Person(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    open lateinit var firstName: String
    open lateinit var lastName: String

    @JsonIgnore
    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], mappedBy = "quotedPersons")
    open lateinit var quotes: List<Quote>

    @JsonIgnoreProperties("person")
    @OneToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.EAGER, mappedBy = "person")
    open lateinit var nicknames: List<Nickname>
}