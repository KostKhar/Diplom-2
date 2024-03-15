package site.stellarburgers.nomoreparties;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetAllIngredients {
    public Response getAllIngredients() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("api/ingredients");
    }
}
