package com.silverwyrm.person.entity

import com.silverwyrm.quote.entity.Quote
import org.jboss.resteasy.spi.touri.MappedBy
import javax.json.bind.annotation.JsonbTransient
import javax.persistence.*

@Entity
@NamedQueries(
        NamedQuery(name = "person.findAll", query = "select p from Person p")
)
@Inheritance(strategy = InheritanceType.JOINED)
open class Person(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    open lateinit var firstName: String
    open lateinit var lastName: String

    @JsonbTransient
    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], mappedBy = "quotedPersons")
    open lateinit var quotes: List<Quote>
}