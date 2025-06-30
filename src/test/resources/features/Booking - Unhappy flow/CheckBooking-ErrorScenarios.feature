@booking

Feature: Validating Check booking details with id
  Background:
    Given I want to login to application with "admin", "password" and received token
  Scenario Outline: To check the booking details with incorrect id
    Given I have booking id <id>
    When I send a Get request to check the booking
    Then I shall receive the error response for incorrect data
    Examples:
      | id     |
      | 0      |
      | 999199 |

  Scenario Outline: To check the booking details with invalid type id
    Given I send a request to check booking details with invalid type "<id>"
    Then I shall receive the error response for incorrect data
    Examples:
      | id     |
      | abde   |
      | &+!    |
      |        |