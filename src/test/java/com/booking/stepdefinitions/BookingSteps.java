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

    @Given("I have booking details payload available with {string}, {string}, {string}, {string}")
    public void iHaveBookingDetailsPayload(String firstname, String lastname, String email, String phone) {
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

    @When("I send a request to create the booking")
    public void iSendRequestToCreateTheBooking() {
        response = given()
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .log().all()
                .when()
                    .post("https://automationintesting.online/booking")
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
                .log().all()
                .pathParam("id", bookingId)
                .when()
                .get("https://automationintesting.online/booking/{id}")
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
}
