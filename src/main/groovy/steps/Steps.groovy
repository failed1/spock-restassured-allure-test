package steps

import io.qameta.allure.Step
import io.restassured.response.Response
import static io.restassured.RestAssured.given

class Steps {

    static serviceUrl = "http://api.weatherstack.com"

    @Step("Get current weather response for <city>")
    static Response getCurrentWeatherForCity(String city) {
        given()
                .when()
                .queryParam("access_key", "90f334d93994d5c98f9ab80149dcaa22")
                .queryParam("query", city)
                .get("${serviceUrl}/current")
    }

    @Step("Negative check for error <code>")
    static Response triggerError(errorCode) {
        def accessKey = "90f334d93994d5c98f9ab80149dcaa22"
        def query = "Moscow"
        switch (errorCode) {
            case 105:
                serviceUrl = "https://api.weatherstack.com"
                break
            case 101:
                accessKey = "invalid"
                break
            case 615:
                query = "dawwetw"
                break
            case 604:
                query = "London;Singapur"
                break
        }
        given()
                .when()
                .queryParam("access_key", accessKey)
                .queryParam("query", query)
                .get("${serviceUrl}/current")
    }
}
