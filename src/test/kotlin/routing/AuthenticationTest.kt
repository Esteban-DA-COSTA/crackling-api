package routing

import com.crackling.api.routing.payloads.UserLoginPayload
import com.crackling.api.routing.payloads.UserRegisterPayload
import com.crackling.domain.entities.UserEntity
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.serialization.json.JsonObject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

const val correctUserEmail = "correct@user.com"
const val correctUserPassword = "correctPassword"
const val correctUserSalt = "salt"
const val correctUserHashedPassword = "mFNWc8B9ppkXF3UvCFWSpK58DBfC9OshwKDL29xn9eo="

class AuthenticationTest {
    

    @BeforeTest
    fun setup() {
        
        val correctMockedEntity = mockk<UserEntity> {
            every { email } returns mockk { every { value } returns correctUserEmail }
            every { password } returns correctUserHashedPassword
            every { salt } returns correctUserSalt
            every { username } returns "username"
        }

        mockkObject(UserEntity.Companion)

        every { UserEntity.findById(correctUserEmail) } returns correctMockedEntity
        
        every { UserEntity.findById(neq(correctUserEmail)) } returns null
        
        // For register test
        every { 
            UserEntity.new(any()) { 
                username = any()
                password = any()
                salt = any()
            } 
        } returns correctMockedEntity
    }
    
    @Test
    fun `should return hello message from auth endpoint`() = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        
        val response = client.get("/auth")
        
        assertEquals(HttpStatusCode.OK, response.status, "Status should be 200")
        assertEquals("Hello from Auth", response.bodyAsText(), "Response should contain hello message")
    }

    @Test
    fun `should be iInvalid on wrong credentials`() = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val email = "test@osterone.wrongmail"
        val password = "WrongPassword"

        val response = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(UserLoginPayload(email, password))
        }
        // On wrong authentification, should be 404
        assertEquals(HttpStatusCode.NotFound, response.status, "Status should be 404")

        // Verify mockk 
        verify { UserEntity.findById(neq(correctUserEmail)) }
    }

    @Test
    fun `should be valid on correct credentials`() = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(UserLoginPayload(correctUserEmail, correctUserPassword))
        }
        assertEquals(HttpStatusCode.OK, response.status, "Status should be 200")
        assertContains(response.body<JsonObject>(), "token", "No token on response body")
        
        verify { UserEntity.findById(correctUserEmail) }
    }
    
    @Test
    fun `should register new user successfully`() = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        
        val username = "newuser"
        val email = "new@user.com"
        val password = "newpassword"
        
        val response = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(UserRegisterPayload(username, email, password))
        }
        
        assertEquals(HttpStatusCode.OK, response.status, "Status should be 200")
        assertContains(response.body<JsonObject>(), "token", "No token on response body")
        
        // Use a more flexible verification approach
        verify { 
            UserEntity.new(eq(email), any()) 
        }
    }
}