package com.silverwyrm.util

import io.restassured.specification.RequestSpecification
import org.mockito.Mockito.`when`

fun RequestSpecification.whn(): RequestSpecification = this.`when`()

fun <T> whn(methodCall: T) = `when`(methodCall)

