import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import site.stellarburgers.nomoreparties.User;

public class BaseTest {
    protected String accessToken;
    protected User user;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    public void deleteData() {
        if (this.accessToken == null) {
            this.user.deleteUser("");
        } else {
            this.user.deleteUser(accessToken);
        }
    }

}
