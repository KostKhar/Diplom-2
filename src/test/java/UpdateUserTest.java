import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.nomoreparties.User;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTest extends BaseTest {
    /*
    Изменение данных пользователя:
с авторизацией,
без авторизации,
Для обеих ситуаций нужно проверить, что любое поле можно изменить. Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.
     */
    // открыли, создали,  вытащили токен

    @Override
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        user = new User(email, password, name);
        accessToken = user.createUser().then().extract().path("accessToken");
    }

    //с авторизацией смена email,  password, name
    @Test
    public void checkUpdateEmailAndPasswordAndEmail() {
        new User(new Faker().internet().emailAddress(), new Faker().internet().password(), new Faker().internet().password())
                .updateUser(accessToken).then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
    }


    // change email and password
    @Test
    public void checkUpdateEmailAndPassword() {
        new User(new Faker().internet().emailAddress(), new Faker().internet().password())
                .updateUser(accessToken).then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
    }


    //с авторизацией смена email
    @Test
    public void checkUpdateEmail() {
        new User(new Faker().internet().emailAddress())
                .updateUser(accessToken).then().assertThat().statusCode(200)
                .and().body("success", equalTo(true));
    }


    //без авторизации
    @Test
    public void checkUpdateUserWithoutAuth() {
        new User(new Faker().internet().emailAddress(), new Faker().internet().password(), new Faker().internet().password())
                .updateUser("")
                .then().assertThat().statusCode(401)
                .and().body("success", equalTo(false)).and().body("message", equalTo("You should be authorised"));
    }
}
