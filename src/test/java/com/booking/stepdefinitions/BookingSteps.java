package com.booking.stepdefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class BookingSteps {
    private Response response;
    private String requestBody;
    private int bookingId;
    private String token;

   @When("I want to login to application with {string}, {string} and received token")
    public void iwantToLogin(String username, String password) {
        requestBody = """
                            {
                             "username": "%s",
                             "password": "%s"
                    }""".formatted(username, password);

        response =given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("https://automationintesting.online/api/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("token");
        System.out.println(token);

    }


    @Then ("I want to logout application with token")
    public void iwantToLogout() {
        requestBody = """
                            {
                             "token": "%s"
                    }""".formatted(token);

        response =given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("https://automationintesting.online/api/auth/logout")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

    }


    @Given("I have booking details payload available with {string}, {string}, {string}, {string}")
    public void iHaveBookingDetailsPayload(String firstname, String lastname, String email, String phone) {
        requestBody = """
                            {
                             "roomid": 1,
                             "firstname": "%s",
                             "lastname": "%s",
                             "depositpaid": true,
                             "email": "%s",
                             "phone": "%s",
                             "bookingdates": {
                              "checkin": "2025-07-03",
                               "checkout": "2025-07-04"
                                             }
                    }""".formatted(firstname, lastname, email, phone);
    }

    @When("I send a request to create the booking")
    public void iSendRequestToCreateTheBooking() {
        response = given()
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .log().all()
                .when()
                    .post("https://automationintesting.online/api/booking")
                .then()
                    .log().all()
                    .extract().response();
    }

    @Then("I will receive the booking details with {string}, {string}, {string}, {string}")
    public void iShouldReceiveTheBookingDetails(String firstname, String lastname, String email, String phone) {
        response.then()
                .statusCode(200)
                .body("booking.firstname", equalTo(firstname))
                .body("booking.lastname", equalTo(lastname))
                .body("booking.email", equalTo(email))
                .body("booking.phone", equalTo(phone))
                .body("booking.size()", greaterThan(0))
                .log().all();
    }

    @Given("I have booking id {int}")
    public void iHaveBookingId(int id) {
        this.bookingId = id;
    }

    @When("I send a Get request to check the booking")
    public void iSendGetRequestToCheckTheBooking() {
        response = given()
             //   .cookie("token", token)
                .log().all()
                .pathParam("id", bookingId)
                .when()
                .get("https://automationintesting.online/api/booking/{id}")
                .then()
                .log().all()
                .extract().response();
    }

    @Then("I shall receive the booking details successfully")
    public void iShallReceiveTheBookingDetails() {
        response.then()
                .statusCode(200)
                .body("bookingid", equalTo(bookingId))
                .body("roomid", notNullValue())
                .body("firstname", equalTo("string"))
                .body("lastname", equalTo("string"))
                .body("depositpaid", is(true))
                .body("email", equalTo("string"))
                .body("phone", notNullValue())
                .body("bookingdates", hasSize(greaterThan(0)))
                .body("checkin", hasSize(greaterThan(0)));
    }

    @Given("I have updated the booking payload with {string}, {string}, {string}, {string}")
    public void iHaveUpdatedBookingPayload(String firstname, String lastname, String email, String phone) {
        requestBody = """
                            {
                            "bookingid": 1,
                             "roomid": 1,
                             "firstname": "%s",
                             "lastname": "%s",
                             "depositpaid": true,
                             "email": "%s",
                             "phone": "%s",
                             "bookingdates": {
                              "checkin": "2025-07-03",
                               "checkout": "2025-07-04"
                                             }
                    }""".formatted(firstname, lastname, email, phone);
    }

    @When("I want to update the booking request")
    public void iUpdateBookingRequest() {
        response = given()
                .header("Content-Type", "application/json")
                //.cookie("token", token)
                .pathParam("id", bookingId)
                .body(requestBody)
                .log().all()
                .when()
                .put("https://automationintesting.online/api/booking/{id}")
                .then()
                .log().all()
                .extract().response();
    }

    @Then("I shall receive the updated booking details successfully for {string}, {string}, {string}, {string}")
    public void iShallReceiveUpdatedBookingDetails(String firstname, String lastname, String email, String phone) {
        response.then()
                .statusCode(200)
                .body("bookingid", equalTo(bookingId))
                .body("roomid", notNullValue())
                .body("firstname", equalTo(firstname))
                .body("lastname", equalTo(lastname))
                .body("depositpaid", is(true))
                .body("email", equalTo(email))
                .body("phone", equalTo(phone))
                .body("bookingdates", hasSize(greaterThan(0)))
                .body("checkin", hasSize(greaterThan(0)));
    }
}
