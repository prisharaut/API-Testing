@booking

Feature: Delete booking details with id
#Background:
   # Given I want to login to application with "<username>", "<password>" and received token
  Scenario Outline: To delete the booking details with id
    Given I have booking id <id>
    When I send a request to Delete booking details based on id
    Then I shall receive the error response for incorrect data
    #And I want to logout application with token
    Examples:
      | id | username | password |
      | 0  | Johndoe  | Johndoe1 |
      | 9911992299  | Johndoe  | Johndoe1 |