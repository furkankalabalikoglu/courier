package com.migros;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CourierApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    public void validates_internal_error_when_undefined_courier() {

        given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/api/movement/9999")
                .then()
                .statusCode(500);
    }

    @Test
    public void validates_ok_when_defined_courier() {

        given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/api/movement/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void validates_body_when_defined_courier() {

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/api/movement/1");

        assertThat(response.path("message").toString(), equalTo("Furkan Kalabalikoglu (ID: 1) adlı kurye 111.18957696002943 km mesafe katetmiştir."));
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void insert_log_successfully() {

        Map body = new HashMap<>();
        body.put("courier_id", 2);
        Map location = new HashMap<>();
        location.put("latitude", 40.33);
        location.put("longitude", 29.12);
        body.put("location", location);
        body.put("log_date", "2021-12-12 01:03:01");

        Response response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("http://localhost:" + port + "/api/movement");

        assertThat(response.path("message").toString(), equalTo("Ahmet Acar (ID: 2) adlı kuryenin hareketi kaydedildi."));
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void insert_log_fail() {

        Map body = new HashMap<>();
        body.put("courier_id", 2);
        Map location = new HashMap<>();
        location.put("latitude", 40.33);
        location.put("longitude", 29.12);
        body.put("location", location);
        body.put("log_date", "2021-12-sgrbr12 01:03:01");

        Response response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("http://localhost:" + port + "/api/movement");

        assertThat(response.path("error").toString(), equalTo("Kurye hareketi kaydedilirken hata oluştu."));
        assertThat(response.statusCode(), equalTo(500));
    }
}
