@booking

Feature: Validating Update booking details with different data
  Scenario Outline: To validate update the booking details with invalid data
    Given I have booking id <id>
    And I have updated the booking payload with "<firstname>", "<lastname>", "<email>", "<phone>"
    When I want to update the booking request
    Then I shall receive the error response for incorrect data
    Examples:
      | id  | firstname | lastname | email | phone |
      | 00 | John | Doe | johndoe@gmail.com | 32490572183 |
      | 1 |       | Doe | johndoe@gmail.com | 32490572183 |
      | 1 | John  |     | johndoe@gmail.com | 32490572183 |
      | 1 | John  | Doe |                   | 32490572183 |
      | 1 | John | Doe | johndoe@gmail.com |  |
      | 1 | John | Doe | johndoe@notanemail.com | 32490572183 |
      | 1 | John | Doe | johndoe@gmail.com | 324abcd2183 |
