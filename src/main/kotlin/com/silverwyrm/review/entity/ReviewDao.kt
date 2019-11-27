package com.silverwyrm.review.entity

import com.silverwyrm.util.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
open class ReviewDao: AdvancedPanacheRepository<Review>, PanacheRepository<Review> {
}