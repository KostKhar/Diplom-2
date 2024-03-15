package site.stellarburgers.nomoreparties;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetAllOrders {

    public Response getAllOrdersOfUser(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .get("api/orders");
    }
}
