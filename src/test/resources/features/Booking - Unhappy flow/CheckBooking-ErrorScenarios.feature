@booking

Feature: Validating Check booking details with id
  #Background:
   # Given I want to login to application with "<username>", "<password>" and received token
  Scenario Outline: To check the booking details with incorrect id
    Given I have booking id <id>
    When I send a Get request to check the booking
    Then I shall receive the error response for incorrect data
    Examples:
      | id     | username | password |
      | 0      | Johndoe  | Johndoe1 |
      | 999199 | Johndoe  | Johndoe1 |

  Scenario Outline: To check the booking details with invalid type id
    Given I send a request to check booking details with invalid type "<id>"
    Then I shall receive the error response for incorrect data
    Examples:
      | id     | username | password |
      | abde   | Johndoe  | Johndoe1 |
      | &+!    | Johndoe  | Johndoe1 |
      |        | Johndoe  | Johndoe1 |