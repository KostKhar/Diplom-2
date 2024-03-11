import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.nomoreparties.RequestBodyOrder;
import site.stellarburgers.nomoreparties.ResponseBodyOrder;
import site.stellarburgers.nomoreparties.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CreateOrderTest extends BaseTest {


    //    с авторизацией,
    @Test
    public void checkCreateOrderWithAuth() {
        user = new User("yaa33n@test1.ru", "Pass1234", "Nick1");
        accessToken = user.createUser().then().extract().path("accessToken");

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of("61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa6d"));
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
        assertTrue(response.as(ResponseBodyOrder.class).getOrder().getNumber() != 0);
    }

//    без авторизации,

    @Test
    public void checkCreateOrderWithoutAuth() {
        user = new User("yaane@test1.ru", "Pass1234", "Nick1");
        accessToken = user.createUser().then().extract().path("accessToken");

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of("61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa6d"));
        Response response = requestBodyOrder.createOrder(" ");

        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
        assertTrue(response.as(ResponseBodyOrder.class).getOrder().getNumber() != 0);
    }

    //    с ингредиентами,
    @Test
    public void checkCreateOrderWithIngredients() {
        user = new User("yaane@test1.ru", "Pass1234", "Nick1");
        accessToken = user.createUser().then().extract().path("accessToken");

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of("61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa6d"));
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
        assertTrue(response.as(ResponseBodyOrder.class).getOrder().getNumber() != 0);
    }
//    без ингредиентов,

    @Test
    public void checkCreateOrderWithoutIngredients() {
        user = new User("yaane@test1.ru", "Pass1234", "Nick1");
        accessToken = user.createUser().then().extract().path("accessToken");

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder();
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(400)
                .and().body("success", equalTo(false));
    }

    //    с неверным хешем ингредиентов.
    @Test
    public void checkCreateOrderWithInvalidHash() {
        user = new User("yaane@test1.ru", "Pass1234", "Nick1");
        accessToken = user.createUser().then().extract().path("accessToken");

        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of("61c0c5a33371d1f82001bdaaa72",
                "61c0c5a71d1f8200144bdaaa6f",
                "61c0c5a71d1f832001bdaaa6d"));
        Response response = requestBodyOrder.createOrder(accessToken);

        response.then().assertThat().statusCode(500);
    }

}
