package com.silverwyrm.group.control

import com.silverwyrm.group.entity.Group
import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
open class GroupDao: PanacheRepository<Group>, AdvancedPanacheRepository<Group> {
}