import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Filetosend entity.
 */
class FiletosendGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Filetosend entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJSON
        .check(header.get("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all filetosends")
            .get("/api/filetosends")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new filetosend")
            .post("/api/filetosends")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "filename":"SAMPLE_TEXT", "subject":"SAMPLE_TEXT", "filetoupload":null, "recipientemailid":"SAMPLE_TEXT", "recipientname":"SAMPLE_TEXT", "addsigner":"SAMPLE_TEXT", "addcc":"SAMPLE_TEXT", "addbulk":"SAMPLE_TEXT", "currentlocation":"SAMPLE_TEXT", "destinationlocation":"SAMPLE_TEXT", "fileinitiatedon":"2020-01-01T00:00:00.000Z", "fileclosedon":"2020-01-01T00:00:00.000Z", "dispatchno":"SAMPLE_TEXT", "status":null}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_filetosend_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created filetosend")
                .get("${new_filetosend_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created filetosend")
            .delete("${new_filetosend_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
