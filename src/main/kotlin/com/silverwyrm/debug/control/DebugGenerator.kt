package com.silverwyrm.debug.control

import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.person.entity.Person
import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
open class DebugGenerator {

    @Inject
    open lateinit var quoteDao: QuoteDao

    @Inject
    open lateinit var personDao: PersonDao

    @Inject
    open lateinit var userDao: UserDao

    open fun generateSomeData(){

        val jan = Person().apply { firstName="Jan"; lastName="Neubauer" }
        val erik = QuoteUser().apply { firstName="Erik"; lastName="Mayerhofer"; sub="eriksGoogleSub" }
        val wahli = Person().apply { firstName="Max"; lastName="Wal" }

        personDao.add(jan)
        userDao.add(erik)
        personDao.add(wahli)

        val quotes = arrayOf(
                Quote().apply { text="Kinan wir ned afoch getBlob() mochn?"; quotedPersons=listOf(jan); quoter=erik },
                Quote().apply { text="E: Du bist böse wast du des?\nW: Jo."; quotedPersons=listOf(erik, wahli); quoter=erik }
        )
        quotes.forEach { quoteDao.add(it) }
    }
}