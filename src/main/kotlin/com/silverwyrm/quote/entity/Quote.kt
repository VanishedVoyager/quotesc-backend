package com.silverwyrm.quote.entity

import javax.persistence.*

@Entity
@NamedQuery(name = "quote.findAll", query = "select q from Quote q")
data class Quote(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
    public lateinit var text: String


}