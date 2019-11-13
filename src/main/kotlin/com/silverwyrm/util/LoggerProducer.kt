package com.silverwyrm.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.enterprise.inject.spi.InjectionPoint

@ApplicationScoped
open class LoggerProducer {
    @Produces
    fun produceLogger(injectionPoint: InjectionPoint): Logger {
        return LoggerFactory.getLogger(injectionPoint.bean.beanClass)
    }
}