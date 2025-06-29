@auth

Feature: To validate token
  Scenario Outline: Login
    When I want to login to application with "<username>", "<password>" and received token
    Then I will validate the token recieved
    Examples:
      | username   | password |
      | Johndoe        | johndoe1 |