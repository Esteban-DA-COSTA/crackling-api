package routing

import com.crackling.api.routing.payloads.UserLoginPayload
import com.crackling.domain.entities.TeamEntity
import com.crackling.domain.entities.UserEntity
import com.crackling.domain.models.team.TeamDTO
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.v1.jdbc.SizedCollection
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TeamRoutingTest {
    lateinit var token: String
    
    @BeforeTest
    fun setup() {
        val correctMockedEntity = mockk<UserEntity> {
            every { email } returns mockk { every { value } returns correctUserEmail }
            every { password } returns correctUserHashedPassword
            every { salt } returns correctUserSalt
            every { username } returns "username"
        }
        mockkObject(UserEntity.Companion, TeamEntity.Companion)
        every { UserEntity.findById(correctUserEmail) } returns correctMockedEntity
        
    }
    
    @Test
    fun `should be authenticated`() = testApplication {
        environment { 
            config = ApplicationConfig("application-test.yaml")
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(UserLoginPayload(correctUserEmail, correctUserPassword))
        }.body<JsonObject>()["token"]?.let { token = it.jsonPrimitive.content }
        
        
        every { TeamEntity.all() } returns SizedCollection(emptyList())
        every { TeamEntity.findById(1) } returns null
        every { TeamEntity[1] } throws EntityNotFoundException(mockk<EntityID<Int>>(relaxed = true) , TeamEntity)
        every { TeamEntity.findByIdAndUpdate(1, any()) } returns null
        
        var response = client.get("/teams")
        assertEquals(HttpStatusCode.Unauthorized, response.status, "Only authenticated access")
        response = client.get("/teams/1")
        assertEquals(HttpStatusCode.Unauthorized, response.status, "Only authenticated access")
        response = client.post("/teams")
        assertEquals(HttpStatusCode.Unauthorized, response.status, "Only authenticated access")
        response = client.put("/teams/1")
        assertEquals(HttpStatusCode.Unauthorized, response.status, "Only authenticated access")
        response = client.delete("/teams/1")
        assertEquals(HttpStatusCode.Unauthorized, response.status, "Only authenticated access")

        //// Test get all
        response = client.get("/teams") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
        assertEquals(HttpStatusCode.OK, response.status, "Authenticated access should be OK")
        
        //// Test get by ID
        response = client.get("/teams/1") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
        // Not found because mocked, but 404 error indicate that the authorization process is working
        assertEquals(HttpStatusCode.NotFound, response.status, "Authenticated access should be OK")
        
        //// Test add
//        response = client.post("/teams", {
//            headers {
//                append(HttpHeaders.Authorization, "Bearer $token")
//            }
//        })
//        assertEquals(HttpStatusCode.NotFound, response.status, "Only authenticated access")
        
        //// Test modify
        response = client.put("/teams/1") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            contentType(ContentType.Application.Json)
            setBody(TeamDTO(name = "name", description = "description", ))
        }
        assertEquals(HttpStatusCode.NotFound, response.status, "Authenticated access should be OK")
        
        //// Test delete
        response = client.delete("/teams/1", {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        })
        assertEquals(HttpStatusCode.NotFound, response.status, "Only authenticated access")
    }
}