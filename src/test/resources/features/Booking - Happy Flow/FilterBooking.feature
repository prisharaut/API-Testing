@booking

Feature: Filter booking with roomid
  #Background:
   # Given I want to login to application with "<username>", "<password>" and received token
  Scenario Outline: To filter the booking details with roomid
    Given I send a request to filter details based on roomid "<roomid>"
    Then I shall receive the booking for roomid successfully
    #And I want to logout application with token
    Examples:
      | roomid | username | password |
      | 1  | Johndoe  | Johndoe1 |