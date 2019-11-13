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
        val quoteUser = QuoteUser().apply { sub="bigBrainQuoteUser" }

        userDao.add(quoteUser)

        val jan = Person().apply { firstName="Jan"; lastName="Neubauer" }
        val erik = Person().apply { firstName="Erik"; lastName="Mayerhofer" }

        personDao.add(jan)
        personDao.add(erik)

        val quotes = arrayOf(
                Quote().apply { text="Ahhhaahah I bin so BRAIN!N!!"; quotedPersons=listOf(jan); quoter=quoteUser },
                Quote().apply { text="Das Yoinkt den Yettel in den Bettel"; quotedPersons=listOf(erik); quoter=quoteUser }
        )
        quotes.forEach { quoteDao.add(it) }
    }
}