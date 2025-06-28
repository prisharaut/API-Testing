@booking

Feature: Check booking details with id
  Scenario Outline: To check the booking details with id
    Given I have booking id <id>
    When I send a Get request to check the booking
    Then I shall receive the booking details successfully
    Examples:
      | id   |
      | 5 |