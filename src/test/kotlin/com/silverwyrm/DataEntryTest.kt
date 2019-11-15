package com.silverwyrm

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.h2.H2DatabaseTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.assertj.db.api.Assertions.assertThat
import org.assertj.db.type.Table
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.util.*
import javax.inject.Inject
import javax.json.Json
import javax.json.JsonObject
import javax.sql.DataSource

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DataEntryTest {

    @Inject
    open lateinit var dataSource: DataSource

    @Test
    @Order(1)
    fun test01_insertPerson(){
        val uuid = UUID.randomUUID().toString()
        val personFirstName = "UnitTestPerson$uuid"
        val personLastName = "UnitTestPersonLastName$uuid"

        val id = util_postPerson(personFirstName, personLastName)

        val persons = Table(dataSource, TableNames.person)
        assertThat(persons).column("firstName").containsValues(personFirstName)
        assertThat(persons).column("lastName").containsValues(personLastName)
        assertThat(persons).column("id").containsValues(id)
    }

    @Test
    @Order(2)
    fun test02_insertQuote(){
        val uuid = UUID.randomUUID().toString()
        val personFirstName = "UnitTestPerson$uuid"
        val personLastName = "UnitTestPersonLastName$uuid"
        val userSub = "UnitTestUserSub$uuid"
        val quoteText = "UnitTestQuoteText$uuid"

        val persons = Table(dataSource, TableNames.person)
        val firstPersonId = persons.getColumn(0).valuesList.first().value as Long

        val userId = util_postUser(userSub, personFirstName, personLastName)
        val quoteId = util_postQuote(quoteText, listOf(firstPersonId.toInt()), userId)

        val quotes = Table(dataSource, TableNames.quote)
        assertThat(quotes).column("text").hasValues(quoteText)
        assertThat(quotes).column("id").hasValues(quoteId)
        assertThat(quotes).column("quoter_id").hasValues(userId)
        assertThat(quotes).hasNumberOfRows(1)

        val personQuote = Table(dataSource, TableNames.personToQuote)
        assertThat(personQuote).column("quotedpersons_id").containsValues(firstPersonId)
        assertThat(personQuote).column("quotes_id").containsValues(quoteId)
    }

    fun util_postQuote(quoteText: String, quotedPersonIds: List<Int>, quoterId: Int): Int{
        val quotedPersons = Json.createArrayBuilder().apply {
            quotedPersonIds.forEach {
                add(Json.createObjectBuilder(mapOf(
                        "id" to it
                )).build())
            }
        }.build()

        val quoteBody = Json.createObjectBuilder().apply {
            add("text", quoteText)
            add("quotedPersons", quotedPersons)
            add("quoter", quoterId)
        }.build()
        return util_postJsonObject("/quotes", quoteBody)
    }

    fun util_postPerson(firstName: String, lastName: String): Int {
        val personBody = Json.createObjectBuilder(mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "nicknames" to Json.createArrayBuilder().build()
        )).build()

        return util_postJsonObject("/persons", personBody)
    }

    fun util_postUser(sub: String, firstName: String, lastName: String): Int{
        val userBody = Json.createObjectBuilder(mapOf(
                "sub" to sub,
                "firstName" to firstName,
                "lastName" to lastName,
                "nicknames" to Json.createArrayBuilder().build()
        )).build()
        return util_postJsonObject("/users", userBody)
    }

    fun util_postJsonObject(path: String, jsonObject: JsonObject): Int {
        println("PutJsonObject to $path")
        val response = given().log().all().headers(mapOf("Content-Type" to "application/json")).body(jsonObject.toString()).`when`().post(path)
        val responseBody = response.body
        response.then().statusCode(200)
        println(responseBody.prettyPrint())
        return responseBody.jsonPath().getInt("id")
    }
}