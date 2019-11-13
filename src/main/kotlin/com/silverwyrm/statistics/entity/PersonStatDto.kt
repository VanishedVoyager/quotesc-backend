package com.silverwyrm.statistics.entity

import com.silverwyrm.person.entity.Person

data class PersonStatDto(
        val person: Person,
        val quoteCount: Long
) {

}