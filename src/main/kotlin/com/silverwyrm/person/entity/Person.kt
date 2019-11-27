package com.silverwyrm.person.entity

import com.silverwyrm.group.entity.Group
import com.silverwyrm.quoteperson.entity.QuotePerson
import javax.persistence.*

@Entity
@NamedQueries(
        NamedQuery(name = "person.findAll", query = "select p from Person p"),
        NamedQuery(name = "person.countQuotes", query = "select new com.silverwyrm.statistics.entity.PersonStatDto(p, count(q.quote)) from Person p LEFT JOIN p.quotePersons q GROUP BY p.id")
)
@Inheritance(strategy = InheritanceType.JOINED)
open class Person(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    open lateinit var firstName: String
    open lateinit var lastName: String

    @OneToMany(mappedBy = "person", targetEntity = QuotePerson::class)
    open lateinit var quotePersons: List<QuotePerson>

    @JvmSuppressWildcards
    @ManyToMany(targetEntity = Group::class)
    open lateinit var groups: List<Group>

//    @JsonIgnoreProperties("person")
//    @OneToMany(cascade = [CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.EAGER, mappedBy = "person")
//    open lateinit var nicknames: List<Nickname>
}