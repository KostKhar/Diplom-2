package site.stellarburgers.nomoreparties;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class User {

    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Response createUser() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(this)
                .when()
                .post("api/auth/register");
    }

    public Response loginUser() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(this)
                .when()
                .post("api/auth/login");
    }

    public Response updateUser(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .and()
                .body(this)
                .when()
                .patch("/api/auth/user");
    }

    public Response deleteUser(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .and()
                .body(this)
                .when()
                .delete("/api/auth/user");
    }


}
