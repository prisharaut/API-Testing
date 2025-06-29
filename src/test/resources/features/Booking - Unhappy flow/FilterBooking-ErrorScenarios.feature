@booking

Feature: Validating booking filter for roomid
  #Background:
   # Given I want to login to application with "<username>", "<password>" and received token
  Scenario Outline: To validate the booking filter with invalid roomid
    Given I send a request to filter details based on roomid "<roomid>"
    Then I shall receive the error response for incorrect data
    #And I want to logout application with token
    Examples:
      | roomid | username | password |
      |   | Johndoe  | Johndoe1 |
      | abc  | Johndoe  | Johndoe1 |
      | &?*  | Johndoe  | Johndoe1 |

  #Background:
   # Given I want to login to application with "<username>", "<password>" and received token
  Scenario Outline: To validate the booking filter with incorrect roomid
    Given I send a request to filter details based on roomid "<roomid>"
    Then I shall receive the empty response summary
    #And I want to logout application with token
    Examples:
      | roomid | username | password |
      | 9991119992  | Johndoe  | Johndoe1 |