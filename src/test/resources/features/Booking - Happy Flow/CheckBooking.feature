@booking

Feature: Check booking details with id
  Scenario Outline: To check the booking details with id
    Given I want to login to application with "<username>", "<password>" and received token
    And I have booking id <id>
    When I send a Get request to check the booking
    Then I shall receive the booking details successfully
    And I want to logout application with token
    Examples:
      | id | username | password |
      | 1  | Johndoe  | Johndoe1 |