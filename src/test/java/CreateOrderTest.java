import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.nomoreparties.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CreateOrderTest extends BaseTest {
    private String ingredient;
    private String ingredient1;
    private String ingredient2;


    @Override
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        user = new User(email, password, name);
        accessToken = user.createUser().then().extract().path("accessToken");
        ingredient = new GetAllIngredients().getAllIngredients().as(RequestAllIngredients.class).getData().get(0).get_id();
        ingredient1 = new GetAllIngredients().getAllIngredients().as(RequestAllIngredients.class).getData().get(1).get_id();
        ingredient2 = new GetAllIngredients().getAllIngredients().as(RequestAllIngredients.class).getData().get(2).get_id();
    }


    //    с авторизацией,
    @Test
    public void checkCreateOrderWithAuth() {

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of(ingredient, ingredient1, ingredient2));
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
        assertTrue(response.as(ResponseBodyOrder.class).getOrder().getNumber() > 0);
    }

//    без авторизации,

    @Test
    public void checkCreateOrderWithoutAuth() {

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of(ingredient, ingredient1, ingredient2));
        Response response = requestBodyOrder.createOrder(" ");

        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
        assertTrue(response.as(ResponseBodyOrder.class).getOrder().getNumber() > 0);
    }

    //    с ингредиентами,
    @Test
    public void checkCreateOrderWithIngredients() {

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of(ingredient, ingredient1, ingredient2));
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
        assertTrue(response.as(ResponseBodyOrder.class).getOrder().getNumber() > 0);
    }
//    без ингредиентов,

    @Test
    public void checkCreateOrderWithoutIngredients() {

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder();
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(400)
                .and().body("success", equalTo(false));
    }

    //    с неверным хешем ингредиентов.
    @Test
    public void checkCreateOrderWithInvalidHash() {
        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of(ingredient + "112", ingredient1 + "ett", ingredient2 + "125"));
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(500);
    }

}
