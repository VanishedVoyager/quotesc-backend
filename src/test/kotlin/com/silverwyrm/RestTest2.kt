package com.silverwyrm

import com.silverwyrm.nickname.boundary.Yeet
import com.silverwyrm.nickname.control.NicknameDao
import com.silverwyrm.nickname.entity.Nickname
import com.silverwyrm.util.whn
import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.test.Mock
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.h2.H2DatabaseTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.path.json.JsonPath
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.enterprise.inject.Produces
import javax.inject.Inject


@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource::class)
class RestTest2 {

    @Produces
    @Mock
    private var nicknameDao: NicknameDao = getNewDao2()

    @Test
    fun test01_nicknames(){
        println("In Test")
        println(nicknameDao)
        println(nicknameDao.test())

        val jsonPath = RestAssured.given().whn().get("/nickname").then().statusCode(200).extract().jsonPath()

        Assertions.assertThat(jsonPath.getList<JsonPath>(".")).isNotEmpty
        Assertions.assertThat(jsonPath.getString("[0].text")).isEqualTo("TestNickname2")
    }

    companion object {
        fun getNewDao2(): NicknameDao {
            println("Producer invoked")
            return Mockito.mock(NicknameDao::class.java).also {

                val nicknameQuery = Mockito.mock(PanacheQuery::class.java) as PanacheQuery<Nickname>
                whn(nicknameQuery.list<Nickname>()).thenReturn(listOf(Nickname().apply { text="TestNickname2" }))

                println("NicknameDao mocked")
                whn(it.test()).thenReturn("I am mocked")
                whn(it.findAll()).thenReturn(nicknameQuery)
                Unit
            }
        }
    }
}