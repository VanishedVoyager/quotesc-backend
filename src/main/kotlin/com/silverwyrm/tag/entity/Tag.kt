package com.silverwyrm.tag.entity

import com.silverwyrm.quote.entity.Quote
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
open class Tag(
        @Id
        @GeneratedValue
        public var id: Long? = null
) {
        open lateinit var name: String

        @JvmSuppressWildcards
        @ManyToMany(mappedBy = "tags", targetEntity = Quote::class)
        open lateinit var quotes: List<Quote>
}