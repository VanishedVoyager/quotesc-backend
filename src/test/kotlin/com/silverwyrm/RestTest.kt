package com.silverwyrm

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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.enterprise.inject.Produces
import javax.inject.Inject


@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource::class)
class RestTest {
    @Produces
    @Mock
    private var nicknameDao: NicknameDao = getNewDao()

    @Test
    fun test01_nicknames(){
        println("In Test")
        println(nicknameDao)
        println(nicknameDao.test())

        val jsonPath = RestAssured.given().whn().get("/nickname").then().statusCode(200).extract().jsonPath()

        assertThat(jsonPath.getList<JsonPath>(".")).isNotEmpty
        assertThat(jsonPath.getString("[0].text")).isEqualTo("TestNickname")
    }

    companion object {
        fun getNewDao(): NicknameDao {
            println("Producer invoked")
            return Mockito.mock(NicknameDao::class.java).also {

                val nicknameQuery = Mockito.mock(PanacheQuery::class.java) as PanacheQuery<Nickname>
                whn(nicknameQuery.list<Nickname>()).thenReturn(listOf(Nickname().apply { text="TestNickname!" }))

                println("NicknameDao mocked")
                whn(it.test()).thenReturn("I am mocked")
                whn(it.findAll()).thenReturn(nicknameQuery)
                Unit
            }
        }
    }
}



