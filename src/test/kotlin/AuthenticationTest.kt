import com.crackling.api.routing.payloads.UserLoginPayload
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class AuthenticationTest {

    @Test
    fun shouldBeInvalidOnWrongCredentials() = testApplication {
        environment {
            config = ApplicationConfig("application.conf")
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
        assert(response.status == HttpStatusCode.NotFound)
    }
    
    @Test
    fun shouldBeValidOnCorrectCredentials() = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        
        val email = System.getenv("TEST_VALID_USER_EMAIL")
        val pass = System.getenv("TEST_VALID_USER_PASSWORD")
        
        val response = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(UserLoginPayload(email, pass))
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }
}