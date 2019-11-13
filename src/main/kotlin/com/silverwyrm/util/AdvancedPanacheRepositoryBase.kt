package com.silverwyrm.util

import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase

interface AdvancedPanacheRepositoryBase<Entity, Key> {
    fun refresh(entity: Entity) {
        Panache.getEntityManager().refresh(entity)
    }

    fun merge(entity: Entity) {
        Panache.getEntityManager().merge(entity)
    }

    fun mergeAndFlush(entity: Entity) {
        merge(entity)
        Panache.getEntityManager().flush()
    }
}