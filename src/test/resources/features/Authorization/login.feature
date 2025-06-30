@auth

Feature: To Login
  Scenario Outline: Login
    When I want to login to application with "<username>", "<password>" and received token
    Examples:
      | username   | password |
      | admin        | password |