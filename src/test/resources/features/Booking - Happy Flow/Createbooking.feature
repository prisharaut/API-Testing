@booking

Feature: Create new booking with user details
  Scenario Outline: To create the new booking with user details
    Given I have booking details payload available with "<firstname>", "<lastname>", "<email>", "<phone>"
    When I send a request to create the booking
    Then I will receive the booking details with "<firstname>", "<lastname>", "<email>", "<phone>"
    Examples:
      | firstname   | lastname | email | phone |
      | John        | Doe      | johndoe@gmail.com | 32490572183 |