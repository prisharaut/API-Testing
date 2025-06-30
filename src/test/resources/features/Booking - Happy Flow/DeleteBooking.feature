@booking

Feature: Delete booking details with id
Background:
    Given I want to login to application with "admin", "password" and received token
  Scenario Outline: To delete the booking details with id
    Given I have booking id <id>
    When I send a request to Delete booking details based on id
    Then The booking details deleted successfully
    Examples:
      | id |
      | 1  |