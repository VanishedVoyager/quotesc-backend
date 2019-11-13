package com.silverwyrm.nickname.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.silverwyrm.person.entity.Person
import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import javax.persistence.*

@Entity
class Nickname(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    lateinit var person: Person

    lateinit var text: String
}