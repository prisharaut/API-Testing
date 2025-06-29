@booking

Feature: Update booking details with id
  Scenario Outline: To update the booking details with id
    Given I have booking id <id>
    And I have updated the booking for bookingid <id> payload with "<roomid>", "<firstname>", "<lastname>", "<email>", "<phone>", "<checkin>", "<checkout>"
    When I want to update the booking request
    Then I shall receive the updated booking details successfully for "<firstname>", "<lastname>", "<email>", "<phone>"
    Examples:
      | id | roomid  | firstname | lastname | email | phone |checkin | checkout |
      | 1 | 1 | hello | world | hellloworld@test.com | 32490565114 |2025-07-03| 2025-07-04 |