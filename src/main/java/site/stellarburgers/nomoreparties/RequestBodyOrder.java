package site.stellarburgers.nomoreparties;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RequestBodyOrder {
    private List<String> ingredients = new ArrayList<>();

    public RequestBodyOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public RequestBodyOrder() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Response createOrder(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .and()
                .body(this)
                .when()
                .post("api/orders");
    }

}
