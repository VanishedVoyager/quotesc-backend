package com.silverwyrm.utils

import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase

interface AdvancedPanacheRepositoryBase<Entity, Id> {
    open fun merge(entity: Entity){
        Panache.getEntityManager().merge(entity)
    }

    open fun refresh(entity: Entity) {
        Panache.getEntityManager().refresh(entity)
    }
}