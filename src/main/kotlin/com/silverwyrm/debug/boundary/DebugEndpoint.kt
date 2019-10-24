package com.silverwyrm.debug.boundary

import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.person.entity.Person
import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import javax.inject.Inject
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/debug")
open class DebugEndpoint {

    @Inject
    lateinit var quoteDao: QuoteDao

    @Inject
    lateinit var personDao: PersonDao

    @Inject
    lateinit var userDao: UserDao

    @POST
    @Path("/generate")
    fun genTestData(): Response {

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

        return Response.ok("${quotes.size} Persons and ${quotes.size} Quotes added.").build()
    }
}