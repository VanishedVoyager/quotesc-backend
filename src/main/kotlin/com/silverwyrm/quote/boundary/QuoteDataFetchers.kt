package com.silverwyrm.quote.boundary

import com.silverwyrm.graphql.GraphQLField
import com.silverwyrm.graphql.TransactionalDataFetcher
import com.silverwyrm.nickname.control.NicknameDao
import com.silverwyrm.nickname.entity.Nickname
import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.quoteperson.control.QuotePersonDao
import com.silverwyrm.quoteperson.entity.QuotePerson
import com.silverwyrm.user.control.UserDao
import graphql.schema.DataFetcher
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class QuoteDataFetchers {
    @Inject
    lateinit var quoteDao: QuoteDao

    @GraphQLField("quotes")
    val quotes = DataFetcher {
        quoteDao.findAll().list<Quote>()
    }
}

@ApplicationScoped
class QuoteDataMutator {
    @Inject
    lateinit var personDao: PersonDao

    @Inject
    lateinit var quoteDao: QuoteDao

    @Inject
    lateinit var nicknameDao: NicknameDao

    @Inject
    lateinit var quotePersonDao: QuotePersonDao

    @Inject
    lateinit var userDao: UserDao

    val create = TransactionalDataFetcher { env ->
        val rawInput = env.getArgument<LinkedHashMap<String, Any>>("quote")

        val input = QuoteCreationInput().apply {
            text = rawInput["text"] as String
            brain = rawInput["brain"] as Int
            persons = (rawInput["persons"] as List<LinkedHashMap<String, Any>>).map { pers ->
                NicknamedPersonInput().apply {
                    personId = (pers["personId"] as Int).toLong()
                    nickname = pers["nickname"] as String
                }
            }
        }

        println(input)

        val generatedQuotePersons = input.persons.map {
            QuotePerson().apply {
                nickname = nicknameDao.find("text", it.nickname).singleResultOptional<Nickname>().orElseGet { Nickname().apply { text = it.nickname } }
                person = personDao.findByIdOptional(it.personId).orElseThrow { IllegalArgumentException("Could not find Person with id ${it.personId}") }
            }
        }

        val quote = Quote().apply {
            text = input.text
            brain = input.brain!!
            quotePersons = generatedQuotePersons
            quoter = userDao.getMe()
        }
        quote.quotePersons.forEach {
            it.quote = quote
            nicknameDao.persist(it.nickname)
        }

        quoteDao.persistAndFlush(quote)
        quotePersonDao.persist(quote.quotePersons)
        quote
    }
}

class QuoteCreationInput() {
    lateinit var text: String
    var brain: Int? = null
    lateinit var persons: List<NicknamedPersonInput>
    override fun toString(): String {
        return "QuoteCreationInput(text='$text', brain=$brain, persons=$persons)"
    }


}

class NicknamedPersonInput() {
    var personId: Long? = null
    lateinit var nickname: String
    override fun toString(): String {
        return "NicknamedPersonInput(personId=$personId, nickname='$nickname')"
    }


}