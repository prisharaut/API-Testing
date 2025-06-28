@auth

Feature: To Logout
  Scenario Outline: Logout
    Given I want to login to application with "<username>", "<password>" and received token
    Then I want to logout application with token
    Examples:
      | username | password |
      | Johndoe  | Johndoe1 |