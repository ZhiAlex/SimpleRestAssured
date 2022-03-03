package com.zhialex.apitests;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.Is.is;

public class CreatePersonTest extends BaseTest {

    int userId = 2;

    @Test
    @DisplayName("user login check")
    void checkLoginTest() {

        JSONObject person = new JSONObject();
        person.put("email", "eve.holt@reqres.in");
        person.put("password", "cityslicka");

        given()
                .contentType(JSON)
                .body(person.toJSONString())
                .when()
                .post(baseUrl + "/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .extract().response();
    }

    @Test
    @DisplayName("user registration check")
    void registrationTest() {

        JSONObject person = new JSONObject();
        person.put("email", "eve.holt@reqres.in");
        person.put("password", "pistol");

        given()
                .contentType(JSON)
                .body(person.toJSONString())
                .when()
                .post(baseUrl + "/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Get all users on 2nd page")
    void getUsersTest() {

        Response response = get(baseUrl + "/users?page=2").then().statusCode(200).extract().response();
        List<String> users = response.jsonPath().getList("data.first_name");
        System.out.println(users);
        Assertions.assertTrue(users.contains("Tobias"));
    }

    @Test
    @DisplayName("get user by id")
    void getUserByIdTest() {

        get(baseUrl + "/users/" + userId)
                .then()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @Test
    @DisplayName("delete user check")
    void deleteUserTest() {

        delete(baseUrl + "/users/" + userId)
                .then()
                .statusCode(204);
    }
}
