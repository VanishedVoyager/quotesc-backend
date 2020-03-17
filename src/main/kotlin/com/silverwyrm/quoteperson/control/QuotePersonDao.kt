package com.silverwyrm.quoteperson.control

import com.silverwyrm.quoteperson.entity.QuotePerson
import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
open class QuotePersonDao: AdvancedPanacheRepository<QuotePerson>, PanacheRepository<QuotePerson> {
}