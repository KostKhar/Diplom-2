import io.restassured.RestAssured;
import org.junit.Test;
import site.stellarburgers.nomoreparties.User;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTest extends BaseTest {
//   открыли урл, создали пользователя


    @Override
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        user = new User("yaan@test1.ru", "Pass1234", "Nick1");
        accessToken = user.createUser().then().extract().path("accessToken");
    }

    //    логин под существующим пользователем,
    @Test
    public void checkLoginUser() {
        user = new User("yaan@test1.ru", "Pass1234");
        user.loginUser().then().assertThat().statusCode(200).and().body("success", equalTo(true));
    }

    //    логин с неверным логином и паролем.
    @Test
    public void checkLoginUserWithIncorrectPassword() {
        user = new User("yan@test1.ru", "Pass12345");
        user.loginUser().then().assertThat().statusCode(401).and().body("success", equalTo(false));
    }

}
