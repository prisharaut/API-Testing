@booking

Feature: Check booking details with id
  Background:
    Given I want to login to application with "admin", "password" and received token
  Scenario Outline: To check the booking details with id
    And I have booking id <id>
    When I send a Get request to check the booking
    Then I shall receive the booking details successfully
    #And I want to logout application with token
    Examples:
      | id |
      | 1  |