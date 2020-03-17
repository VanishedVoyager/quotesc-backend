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
import com.silverwyrm.review.control.ReviewDao
import com.silverwyrm.review.entity.Review
import com.silverwyrm.tag.control.TagDao
import com.silverwyrm.tag.entity.Tag
import com.silverwyrm.user.control.UserDao
import com.silverwyrm.user.entity.QuoteUser
import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import java.time.LocalDateTime
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject
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

    @Inject
    open lateinit var tagDao: TagDao

    @Inject
    open lateinit var reviewDao: ReviewDao

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
        val erik = QuoteUser().apply { person = Person().apply { firstName="Erik"; lastName="Mayerhofer"; groups=listOf(group_ww, group_htl) }; subject="eriksGoogleSub";  }
        val felix = QuoteUser().apply { person = Person().apply {  firstName="Felix"; lastName="Drebler"; groups=listOf(group_ww) }; subject="felixFacebookSub";  }
        val wahli = Person().apply { firstName="Max"; lastName="Wal"; groups=listOf(group_htl) }


        groupDao.persist(group_htl, group_ww)

//        jan.nicknames.forEach { nicknameDao.persist(it) }
//        erik.nicknames.forEach { nicknameDao.persist(it) }
//        wahli.nicknames.forEach { nicknameDao.persist(it) }

        personDao.persist(jan)
        personDao.persist(erik.person)
        personDao.persist(wahli)
        personDao.persist(felix.person)

        Panache.getEntityManager().flush()

        erik.id = erik.person.id
        userDao.persist(erik)
        felix.id = felix.person.id
        userDao.persist(felix)

        val janderdenker = Nickname().apply { text="Der Denker" }
        val erikdergm = Nickname().apply { text="Der Gamemaster" }
        val erikdergute = Nickname().apply { text="Der Gute" }
        val wahlidierakete = Nickname().apply { text="Die Rakete" }

        nicknameDao.persist(janderdenker)
        nicknameDao.persist(erikdergm)
        nicknameDao.persist(erikdergute)
        nicknameDao.persist(wahlidierakete)

        val t_programmer = Tag().apply { name="Programmer" }
        val t_dafak = Tag().apply { name="??!" }
        val t_context = Tag().apply { name="Was zum Kontext?" }

        tagDao.persist(t_programmer, t_dafak, t_context)

        val quotes = arrayOf(
                Quote().apply { text="Kinan wir ned afoch getBlob() mochn?"; quoter=erik; tags=listOf(t_programmer); date= LocalDateTime.now(); brain=8 },
                Quote().apply { text="Mit meine Mutagene undso, jo do werd i voi zur Fledermaus!"; quoter=erik; tags=listOf(t_dafak, t_context); date= LocalDateTime.now(); brain=3 },
                Quote().apply { text="E: Du bist böse wast du des?\nW: Jo."; quoter=erik; tags=listOf(t_context); date= LocalDateTime.now(); brain=5 }
        )

        quotes.forEach { quoteDao.persist(it) }

        val quotePerson = arrayOf(
                QuotePerson().apply { person=jan; quote=quotes[0]; nickname=janderdenker },
                QuotePerson().apply { person=erik.person; quote=quotes[1]; nickname=erikdergm },
                QuotePerson().apply { person=erik.person; quote=quotes[2]; nickname=erikdergute },
                QuotePerson().apply { person=wahli; quote=quotes[2]; nickname=wahlidierakete }
        )

        val reviews = mutableListOf(
                Review().apply { quote=quotes[0]; user=erik; repeat(4) { upvote() } },
                Review().apply { quote=quotes[1]; user=erik; repeat(2) { downvote() } },
                Review().apply { quote=quotes[2]; user=erik; repeat(1) { upvote() } },

                Review().apply { quote=quotes[0]; user=felix; repeat(2) { upvote() } },
                Review().apply { quote=quotes[1]; user=felix; repeat(2) { upvote() } },
                Review().apply { quote=quotes[2]; user=felix; repeat(3) { downvote() } }
        )

        reviewDao.persist(reviews)

        quotePerson.forEach { quotePersonDao.persist(it) }

        Panache.getEntityManager().flush()
    }

    open fun startupEvent(@Observes event: StartupEvent){
        if(generateData){
            generateSomeData()
        }
    }
}