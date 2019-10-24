package com.silverwyrm.person.entity

import com.silverwyrm.quote.entity.Quote
import org.jboss.resteasy.spi.touri.MappedBy
import javax.json.bind.annotation.JsonbTransient
import javax.persistence.*

@Entity
data class Person(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    public lateinit var firstName: String
    public lateinit var lastName: String

    @JsonbTransient
    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], mappedBy = "quotedPersons")
    lateinit var quotes: List<Quote>
}