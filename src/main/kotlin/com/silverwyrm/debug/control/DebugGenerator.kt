package com.silverwyrm.debug.control

import com.silverwyrm.group.control.GroupDao
import com.silverwyrm.group.entity.Group
import com.silverwyrm.nickname.control.NicknameDao
import com.silverwyrm.nickname.entity.Nickname
import com.silverwyrm.person.control.PersonDao
import com.silverwyrm.person.entity.Person
import com.silverwyrm.quote.control.QuoteDao
import com.silverwyrm.quote.entity.Quote
import com.silverwyrm.quoteperson.control.QuotePersonDao
import com.silverwyrm.quoteperson.entity.QuotePerson
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import io.quarkus.arc.config.ConfigProperties
import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Initialized
import javax.enterprise.event.Observes
import javax.inject.Inject
import javax.swing.GroupLayout
import javax.transaction.Transactional

@ApplicationScoped
open class DebugGenerator {

    @Inject
    open lateinit var quoteDao: QuoteDao

    @Inject
    open lateinit var personDao: PersonDao

    @Inject
    open lateinit var userDao: UserDao

    @Inject
    open lateinit var nicknameDao: NicknameDao

    @Inject
    open lateinit var quotePersonDao: QuotePersonDao

    @Inject
    open lateinit var groupDao: GroupDao

    @ConfigProperty(name = "com.silverwyrm.debug.generate-data")
    protected open var generateData: Boolean = false

    @Inject
    open lateinit var logger: Logger

    @Transactional
    open fun generateSomeData(){
        logger.info("Creating debug Data in Database")

        val group_htl = Group().apply { name="Htl Leonding" }
        val group_ww = Group().apply { name="White Wyvern" }

        val jan = Person().apply { firstName="Jan"; lastName="Neubauer"; groups=listOf(group_ww, group_htl) }
        val erik = QuoteUser().apply { firstName="Erik"; lastName="Mayerhofer"; sub="eriksGoogleSub"; groups=listOf(group_ww, group_htl) }
        val wahli = Person().apply { firstName="Max"; lastName="Wal"; groups=listOf(group_htl) }


        groupDao.persist(group_htl, group_ww)

//        jan.nicknames.forEach { nicknameDao.persist(it) }
//        erik.nicknames.forEach { nicknameDao.persist(it) }
//        wahli.nicknames.forEach { nicknameDao.persist(it) }

        personDao.add(jan)
        userDao.add(erik)
        personDao.add(wahli)

        val janderdenker = Nickname().apply { text="Der Denker" }
        val erikdergm = Nickname().apply { text="Der Gamemaster" }
        val erikdergute = Nickname().apply { text="Der Gute" }
        val wahlidierakete = Nickname().apply { text="Die Rakete" }

        nicknameDao.persist(janderdenker)
        nicknameDao.persist(erikdergm)
        nicknameDao.persist(erikdergute)
        nicknameDao.persist(wahlidierakete)

        val quotes = arrayOf(
                Quote().apply { text="Kinan wir ned afoch getBlob() mochn?"; quoter=erik },
                Quote().apply { text="Mit meine Mutagene undso, jo do werd i voi zur Fledermaus!"; quoter=erik },
                Quote().apply { text="E: Du bist böse wast du des?\nW: Jo."; quoter=erik }
        )

        quotes.forEach { quoteDao.persist(it) }

        val quotePerson = arrayOf(
                QuotePerson().apply { person=jan; quote=quotes[0]; nickname=janderdenker },
                QuotePerson().apply { person=erik; quote=quotes[1]; nickname=erikdergm },
                QuotePerson().apply { person=erik; quote=quotes[2]; nickname=erikdergute },
                QuotePerson().apply { person=wahli; quote=quotes[2]; nickname=wahlidierakete }
        )


        quotePerson.forEach { quotePersonDao.persist(it) }

        Panache.getEntityManager().flush()
    }

    open fun startupEvent(@Observes event: StartupEvent){
        if(generateData){
            generateSomeData()
        }
    }
}