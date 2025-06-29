@booking

Feature: Create new booking with user details
  Scenario Outline: To create the new booking with user details
    Given I have booking details for roomid "<roomid>" available with "<firstname>", "<lastname>", "<email>", "<phone>", "<checkin>", "<checkout>"
    When I send a request to create the booking
    Then I will receive the booking details with "<firstname>", "<lastname>", "<email>", "<phone>", "<checkin>", "<checkout>"
    Examples:
      |roomid | firstname   | lastname | email | phone |checkin| checkout |
      | 1 |John        | Doe      | johndoe@gmail.com | 32490572183 |2025-07-03| 2025-07-04|