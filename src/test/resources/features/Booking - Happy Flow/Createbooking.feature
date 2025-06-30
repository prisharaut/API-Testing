@booking

Feature: Create new booking with user details

  Background:
    Given I want to login to application with "admin", "password" and received token

  Scenario Outline: To create the new booking with user details
    Given I have booking details for roomid "<roomid>" available with "<firstname>", "<lastname>", "<email>", "<phone>", "<checkin>", "<checkout>"
    When I send a request to create the booking
    Then I will receive the booking details with "<roomid>", "<firstname>", "<lastname>", "<email>", "<phone>", "<checkin>", "<checkout>"
    Examples:
      |bookingid| roomid | firstname | lastname | email             | phone       | checkin    | checkout   |
      | 10       | random | John      | Doe      | johndoe@gmail.com | 32490572183 | 2025-07-07 | 2025-07-08 |