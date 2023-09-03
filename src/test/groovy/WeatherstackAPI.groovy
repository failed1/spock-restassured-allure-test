import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.restassured.response.Response
import steps.Steps

import static org.hamcrest.Matchers.is

@Epic("weatherstack.com API")
class WeatherstackAPI extends GenericSpecification {

    @Feature("Compare response with expected values")
    def "expected/response validation"() {
        given: "WeatherStack API as AUT"
        when: "Current weather request is sent for selected <city>"

        Response response = Steps.getCurrentWeatherForCity(city)

        then: "should be 200, related fields should match or difference is displayed in log"
        response.then()
                .statusCode(200)
                .body("location.utc_offset", is(utc_offset),
                        "location.country", is(country),
                        "current.wind_speed", is(wind_speed),
                        "current.temperature", is(temperature))
        where:
        id | city       | utc_offset | country          | wind_speed | temperature
        1  | "Belgrade" | "2.0"      | "Serbia"         | 15         | 25
        2  | "Istanbul" | "3.0"      | "Turkey"         | 10         | 25
        3  | "Moscow"   | "3.0"      | "Russia"         | 11         | 25
        4  | "London"   | "1.0"      | "United Kingdom" | 12         | 25
    }

    @Feature("possible integration")
    def "error codes validation"() {
        given: "WeatherStack API as AUT"
        when: "Applying negative data to trigger specific error"

        Response response = Steps.triggerError(errorCode)

        then: "<error> with related code should be fired"
        response.then()
                .body("error.code", is(errorCode))
        where:
        id |  errorCode
        1  |  604
        2  |  101
        3  |  615
        4  |  105
    }
}
