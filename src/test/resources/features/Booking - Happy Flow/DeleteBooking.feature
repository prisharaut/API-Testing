@booking

Feature: Delete booking details with id
#Background:
   # Given I want to login to application with "<username>", "<password>" and received token
  Scenario Outline: To delete the booking details with id
    Given I send a request to Delete booking details based on id <id>
    Then The booking details deleted successfully
    Examples:
      | id | username | password |
      | 1  | Johndoe  | Johndoe1 |