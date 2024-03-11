import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.nomoreparties.GetAllOrders;
import site.stellarburgers.nomoreparties.RequestBodyOrder;
import site.stellarburgers.nomoreparties.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class GetAllOrdersTest extends BaseTest {


    //Получение заказов конкретного пользователя:

    @Override
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        this.user = new User("yaa32n@test1.ru", "Pass1234", "Nick1");
        this.accessToken = user.createUser().then().extract().path("accessToken");
        RequestBodyOrder requestBodyOrder = new RequestBodyOrder(List.of("61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa6d"));
        requestBodyOrder.createOrder(this.accessToken);
    }

    //    авторизованный пользователь
    @Test
    public void checkGetOrdersWithAuth() {
        Response response = new GetAllOrders().getAllOrdersOfUser(accessToken);

        int actual = response.then().extract().path("total");

        response.then().assertThat()
                .statusCode(200).and().body("success", equalTo(true));
        assertTrue(actual > 0);
    }

    //    неавторизованный пользователь.
    @Test
    public void checkGetOrdersWithoutAuth() {
        new GetAllOrders().getAllOrdersOfUser(" ")
                .then().assertThat()
                .statusCode(401).and().body("success", equalTo(false));

    }
}
