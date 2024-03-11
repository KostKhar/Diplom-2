import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.nomoreparties.User;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends BaseTest {

    private Response response;

    //    создать уникального пользователя;
    @Test
    public void createDistinctUser() {
        user = new User("ya000ndex@test.com", "Pass1234", "Nick_Name");
        response = user.createUser();
        accessToken = response.then().extract().path("accessToken");

        response.then().assertThat().statusCode(200).body("success", equalTo(true));
    }

    //    создать пользователя, который уже зарегистрирован;
    @Test
    public void createDoubleUser() {
        user = new User("yandexxxx@test.com", "Pass1234", "Nick_Name");
        accessToken = user.createUser().then().extract().path("accessToken");

        user.createUser().then().assertThat().statusCode(403)
                .body("success", equalTo(false))
                .and().body("message", equalTo("User already exists"));
    }


    //    создать пользователя и не заполнить одно из обязательных полей
    @Test
    public void createUserWithoutName() {
        user = new User("yandex@test.com", "Pass1234");

        user.createUser().then().statusCode(403)
                .body("success", equalTo(false))
                .and().body("message", equalTo("Email, password and name are required fields"));
    }


}
