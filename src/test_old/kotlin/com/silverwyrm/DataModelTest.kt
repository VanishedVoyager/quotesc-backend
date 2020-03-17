package com.silverwyrm

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.h2.H2DatabaseTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.assertj.core.description.Description
import org.assertj.db.api.Assertions.assertThat
import org.assertj.db.type.Table
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.sql.DataSource

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource::class)
open class DataModelTest {

    @Inject
    open lateinit var datasource: DataSource

    @Test
    fun test01_correct_person() {
        val person = Table(datasource, TableNames.person)

        assertThat(person).column("firstName").isText(false)
        assertThat(person).column("id").isNumber(false)
        assertThat(person).column("lastName").isText(false)
        assertThat(person).hasNumberOfColumns(3)
    }

    @Test
    fun test02_correct_quote(){
        val quote = Table(datasource, TableNames.quote)

        assertThat(quote).hasNumberOfColumns(3)
        assertThat(quote).column("text").isText(false)
        assertThat(quote).column("quoter_id").isNumber(false)
        assertThat(quote).column("id").isNumber(false)
    }

    @Test
    fun test03_correct_nickname(){
        val nickname = Table(datasource, TableNames.nickname)

        assertThat(nickname).hasNumberOfColumns(3)
        assertThat(nickname).column("text").isText(false)
        assertThat(nickname).column("id").isNumber(false)
        assertThat(nickname).column("person_id").isNumber(false)
    }

    @Test
    fun test04_correct_user(){
        val user = Table(datasource, TableNames.user)

        assertThat(user).hasNumberOfColumns(2)
        assertThat(user).column("sub").isText(false)
        assertThat(user).column("id").isNumber(false)
    }

    @Test
    fun test05_correct_personQuote(){
        val personQuote = Table(datasource, TableNames.personToQuote)

        assertThat(personQuote).hasNumberOfColumns(2)
    }

}