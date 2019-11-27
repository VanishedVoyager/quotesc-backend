package com.silverwyrm.tag.control

import com.silverwyrm.tag.entity.Tag
import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
open class TagDao: PanacheRepository<Tag>, AdvancedPanacheRepository<Tag> {
}