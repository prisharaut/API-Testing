package com.booking.stepdefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class BookingSteps {
    private Response response;
    private String requestBody;
    private int bookingId;
    private String token;

    //Login POST request to generate token
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

    //Validate POST request to validate the generated token
    @Then("I will validate the token recieved")
    public void iWillValidateTheTokenRecieved() {
        requestBody = """
                            {
                             "token": "%s"
                    }""".formatted(token);

        response =given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("https://automationintesting.online/api/auth/validate")
                .then()
                .log().all()
                .statusCode(200)
                .body("token",equalTo(token))
                .extract().response();
        token = response.jsonPath().getString("token");
        System.out.println(token);
    }

    //Logout POST request to logout as user
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

    //GET request for Summary
    //Step to send GET request with roomid as query parameter
    @Given("I send a request to Get booking summary based on roomid {string}")
    public void iGetBookingSummaryBasedOnRoomid(String roomid) {
        response = given()
                .log().all()
                .queryParam("roomid", roomid)
                .when()
                .get("https://automationintesting.online/api/booking/summary")
                //.get("https://391416e6-7053-44a5-b7dd-894a56931d6a.mock.pstmn.io/api/booking/summary/")
                .then()
                .log().all()
                .extract().response();
    }

    //Step to validate received booking summary response
    @Then("I shall receive the booking summary successfully")
    public void iReceiveBookingSummary() {
        response.then()
                .statusCode(200)
                .body("bookings.size()", greaterThan(0))
                .body("bookings", notNullValue())
                .body("bookings.bookingDates.checkin", everyItem(not(emptyOrNullString())))
                .body("bookings.bookingDates.checkout", everyItem(not(emptyOrNullString())))
                .log().all();
    }

    //Get request for Unavailability
    //Step to send GET request with checkin and checkout dates as query parameter
    @Given("I send a request to check booking unavailability based on {string}, {string}")
    public void iSendARequestToCheckUnavailability(String checkin, String checkout) {
        response = given()
                .cookie("token", token)
                .log().all()
                .queryParam("checkin", checkin)
                .queryParam("checkout", checkout)
                //  .queryParam("roomid", "6")
                .when()
                .get("https://automationintesting.online/api/booking/")
                //.get("https://d34ed2a2-6033-4dab-9617-c1dd3d2b0da5.mock.pstmn.io/api/booking/")
                .then()
                .log().all()
                .extract().response();
    }

    //Step to validate received unavailability response
    @Then("I shall receive the roomid unavailable")
    public void iReceiveRoomIdUnavailable() {
        response.then()
                .statusCode(200)
                //.body("roomid", everyItem(notNullValue()))
                .log().all();
    }

    //Get request for checking booking
    //To check the booking when booking Id is available
    @Given("I have booking id {int}")
    public void iHaveBookingId(int id) {
        this.bookingId = id;
    }

    //Step to send Get request to check booking with bookingid as path parameter
    @When("I send a Get request to check the booking")
    public void iSendGetRequestToCheckTheBooking() {
        response = given()
                .cookie("token", token)
                .log().all()
                .pathParam("id", bookingId)
                .when()
                .get("https://automationintesting.online/api/booking/{id}")
                //.get("https://7692835e-3557-442e-982c-527aed92923c.mock.pstmn.io/api/booking/{id}")
                .then()
                .log().all()
                .extract().response();
    }

    //Step to validate the received booking in response
    @Then("I shall receive the booking details successfully")
    public void iShallReceiveTheBookingDetails() {
        response.then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("roomid", notNullValue())
                .body("firstname", notNullValue())
                .body("lastname", notNullValue())
                .body("depositpaid", instanceOf(Boolean.class))
                .body("email", notNullValue())
                .body("phone", notNullValue())
                .body("bookingdates.checkin", notNullValue())
                .body("bookingdates.checkout", notNullValue());
    }

    //Post request to create new booking
    //Step to create booking payload
    @Given("I have booking details for roomid {string} available with {string}, {string}, {string}, {string}, {string}, {string}")
    public void iHaveBookingDetailsPayload(String roomid, String firstname, String lastname, String email, String phone, String checkin,String checkout) {
        if("random".equalsIgnoreCase(roomid)){
            roomid = String.valueOf(new Random().nextInt(9999)+1);
        }
        requestBody = """
                            {
                             "roomid": "%s",
                             "firstname": "%s",
                             "lastname": "%s",
                             "depositpaid": true,
                             "email": "%s",
                             "phone": "%s",
                             "bookingdates": {
                              "checkin": "%s",
                               "checkout": "%s"
                                             }
                    }""".formatted(roomid,firstname, lastname, email, phone,checkin,checkout);
    }

    //Step to send Post request to create new booking with booking payload
    @When("I send a request to create the booking")
    public void iSendRequestToCreateTheBooking() {
        response = given()
                    .header("Content-Type", "application/json")
                    .cookie("token", token)
                    .body(requestBody)
                    .log().all()
                .when()
                    .post("https://automationintesting.online/api/booking")
                //.post("https://093a6d50-fb54-48c8-9b54-45cae8cea0d6.mock.pstmn.io/api/booking")
                .then()
                    .log().all()
                    .extract().response();
    }

    //Step to validate the Post response with the creation payload
    @Then("I will receive the booking details with {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void iShouldReceiveTheBookingDetails( String roomid ,String firstname, String lastname, String email, String phone, String checkin, String checkout) {
        response.then()
                .statusCode(200)
                .body("booking.bookingid",notNullValue())
                .body("booking.roomid",notNullValue())
                .body("booking.firstname", equalTo(firstname))
                .body("booking.lastname", equalTo(lastname))
                .body("booking.email", equalTo(email))
                .body("booking.phone", equalTo(phone))
                .body("booking.bookingdates.checkin", equalTo(checkin))
                .body("booking.bookingdates.checkout", equalTo(checkout))
                .body("booking.size()", greaterThan(0))
                .log().all();
    }

    //Step to send Delete request to remove the booking details
    @Given("I send a request to Delete booking details based on id")
    public void iSendARequestToDeleteBookingDetailsBasedOnIdId() {
        //RestAssured.baseURI = "https://automationintesting.online/api/booking";
        response = given()
                .log().all()
                .cookie("token",token)
                .pathParam("id", bookingId)
                .when()
                .delete("https://automationintesting.online/api/booking/{id}")
                //.delete("https://f7c78856-efa1-464f-a1c1-b4fcc0e30fcf.mock.pstmn.io/api/booking/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    //To validate booking details are deleted
    @Then("The booking details deleted successfully")
    public void theBookingDetailsDeletedSuccessfully() {
        response.then()
                .statusCode(200)
                .log().all();
    }

    //GET request to filter the booking availability
    //Step to send request to filter the room availability based on roomid as query parameter
    @Given("I send a request to filter details based on roomid {string}")
    public void iSendARequestToFilterDetailsBasedOnRoomidRoomid(String roomid) {
        response = given()
                .cookie("token", token)
                .log().all()
                .queryParam("roomid", roomid)
                .when()
                .get("https://automationintesting.online/api/booking/")
                //.get("https://914e9a33-5ec0-4ffc-8533-de9fed40e586.mock.pstmn.io/api/booking/")
                .then()
                .log().all()
                .extract().response();
    }

    //Step to validate the response based on filtered request by roomid
    @Then("I shall receive the booking for roomid successfully")
    public void iReceiveRoomIdBookingDetails() {
        response.then()
                .statusCode(200)
                .body("bookings.size()", greaterThan(0))
                .body("bookings.bookingid", everyItem(notNullValue()))
                .body("bookings.roomid",everyItem(allOf(not(emptyOrNullString()))))
                .body("bookings.firstname",everyItem(allOf(not(emptyOrNullString()))))
                .body("bookings.lastname", everyItem(allOf(not(emptyOrNullString()))))
                .body("bookings.depositpaid", everyItem(allOf(not(emptyOrNullString()))))
                //   .body("bookings.email", everyItem(allOf(not(emptyOrNullString()))))
                //   .body("bookings.phone", everyItem(allOf(not(emptyOrNullString()))))
                .body("bookings.bookingdates.checkin", everyItem(allOf(not(emptyOrNullString()))))
                .body("bookings.bookingdates.checkout", everyItem(allOf(not(emptyOrNullString()))))
                .log().all();
    }

    //PUT request to update booking
    //Step to send create request payload for PUT request
    @Given("I have updated the booking for bookingid {int} payload with {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void iHaveUpdatedBookingPayload(int id, String roomid,String firstname, String lastname, String email, String phone, String checkin, String checkout) {
        requestBody = """
                            {
                            "bookingid": %d,
                             "roomid": "%s",
                             "firstname": "%s",
                             "lastname": "%s",
                             "depositpaid": true,
                             "email": "%s",
                             "phone": "%s",
                             "bookingdates": {
                              "checkin": "%s",
                               "checkout": "%s"
                                             }
                    }""".formatted(id, roomid, firstname, lastname, email, phone, checkin,checkout);
        System.out.println("Request body: " + requestBody);
    }

    //PUT request to update booking
    //Step to send PUT request to update the booking details with updated payload
    @When("I want to update the booking request")
    public void iUpdateBookingRequest() {
        response = given()
                .header("Content-Type", "application/json")
                .cookie("token", token)
                .pathParam("id", bookingId)
                .body(requestBody)
                .log().all()
                .when()
                .put("https://automationintesting.online/api/booking/{id}")
                //.put("https://4cbb907d-faec-49fa-ab5f-9b8de59d899f.mock.pstmn.io/api/booking/{id}")
                .then()
                .log().all()
                .extract().response();
        System.out.println(response.getBody().asPrettyString());
    }
    //Step to validate the booking details are updated
    @Then("I shall receive the updated booking details successfully for {string}, {string}, {string}, {string}")
    public void iShallReceiveUpdatedBookingDetails(String firstname, String lastname, String email, String phone) {
        response.then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("booking.bookingid",notNullValue())
                .body("booking.roomid", notNullValue())
                .body("booking.firstname", equalTo(firstname))
                .body("booking.lastname", equalTo(lastname))
                .body("booking.depositpaid", notNullValue())
                .body("booking.email", equalTo(email))
                .body("booking.phone", equalTo(phone))
                .body("booking.bookingdates.checkin", notNullValue())
                .body("booking.bookingdates.checkout", notNullValue());
    }

    //Error Scenario Steps
    @Then("I shall receive the error response for incorrect data")
    public void iShallReceiveTheErrorForMissingRoomId() {
        response.then()
                .statusCode(404); //or 400 as per api response
    }

    @Then("I shall receive the error response for incorrect booking details")
    public void iShallReceiveTheErrorForIncorrectBookingDetails() {
        response.then()
                .statusCode(400);
    }

    @When("I send a request to check booking details with invalid type {string}")
    public void iSendARequestToCheckBookingDetailsWithInvalidIdId(String bookingId) {
        response = given()
                //   .cookie("token", token)
                .log().all()
                .pathParam("id", bookingId)
                .when()
                .get("https://automationintesting.online/api/booking/")
                .then()
                .log().all()
                .extract().response();

    }

    @Then("I shall receive the empty response summary")
    public void iShallReceiveTheEmptyResponseSummary() {
        response.then()
                .statusCode(200)
                .body("bookings.size()", equalTo(0))
                .log().all();
    }

}
