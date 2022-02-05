package api.Tests;

import api.PojoAndSpec.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RestTest {

    private static final String SITE_URL = "https://reqres.in/";

    @Test
    public void checkAvatar() {
        Specifications.installSpecification(Specifications.requestSpec(SITE_URL), Specifications.responseSpecCode(200));
        List<UserData> users = given()
                .when()
                .get(SITE_URL + "api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assert.assertTrue(users.stream().allMatch(x-> x.getEmail().endsWith("@reqres.in")));
    }

    @Test
    public void checkSuccessReg() {
        Specifications.installSpecification(Specifications.requestSpec(SITE_URL), Specifications.responseSpecCode(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void checkUnSuccessReg() {
        Specifications.installSpecification(Specifications.requestSpec(SITE_URL), Specifications.responseSpecCode(400));
        String error = "Missing password";
        Register user = new Register("eve.holt@reqres.in", "");
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertNotNull(unSuccessReg.getError());
        Assert.assertEquals(error, unSuccessReg.getError());
    }
}
