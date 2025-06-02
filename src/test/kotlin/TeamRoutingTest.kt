import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class TeamRoutingTest {
    @Test
    fun testTeamRouting() = testApplication {
        environment { 
            config = ApplicationConfig("application-test.yaml")
        }
        val response = client.get("/teams")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}