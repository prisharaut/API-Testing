@booking

Feature: Validating Create new booking with invalid user details
  Scenario Outline: To validate the create new booking with invalid user details
    Given I have booking details for roomid "<roomid>" available with "<firstname>", "<lastname>", "<email>", "<phone>", "<checkin>", "<checkout>"
    When I send a request to create the booking
    Then I shall receive the error response for incorrect booking details
    Examples:
      |roomid| firstname   | lastname     | email             | phone       | checkin  | checkout |
      |      |John         | Doe          | johndoe@gmail.com | 32490572183 |2025-07-03| 2025-07-04|
      |1     |             | Doe          | johndoe@gmail.com | 32490572183 |2025-07-03| 2025-07-04|
      |1     |John         |              | johndoe@gmail.com | 32490572183 |2025-07-03| 2025-07-04|
      |1     |John         | Doe          |                   | 32490572183 |2025-07-03| 2025-07-04|
      |1     |John         | Doe          | johndoe@gmail.com |             |2025-07-03| 2025-07-04|
      |1     |John         | Doe          | johndoe@gmail.com | 32490572183 |          | 2025-07-04|
      |1     |John         | Doe          | johndoe@gmail.com | 32490572183 |2025-07-03|      |
      |999999999 |John         | Doe          | johndoe@gmail.com | 32490572183 |2025-07-03| 2025-07-04|
      |1     |John         | Doe          | johndoe@johndoe.com | 32490572183 |2025-07-03| 2025-07-04|
      |1     |John         | Doe          | johndoe@gmail.com | 324+#572183 |2025-07-03| 2025-07-04|
      |1     |John         | Doe          | johndoe@gmail.com | 32490572183 |2095-07-03| 2095-07-04|

