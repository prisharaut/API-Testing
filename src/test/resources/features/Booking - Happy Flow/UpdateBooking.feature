@booking

Feature: Update booking details with id
  Scenario Outline: To update the booking details with id
    Given I have booking id <id>
    And I have updated the booking payload with "<firstname>", "<lastname>", "<email>", "<phone>"
    When I want to update the booking request
    Then I shall receive the updated booking details successfully for "<firstname>", "<lastname>", "<email>", "<phone>"
    Examples:
      | id  | firstname | lastname | email | phone |
      | 1 | hello | world | hellloworld@test.com | 32490565114 |