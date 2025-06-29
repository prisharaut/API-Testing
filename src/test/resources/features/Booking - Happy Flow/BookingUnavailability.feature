@booking

Feature: Check booking unavailability with dates
  #Background:
   # Given I want to login to application with "<username>", "<password>" and received token
  Scenario Outline: To check the booking unavailability based on dates
    Given I send a request to check booking unavailability based on "<checkin>", "<checkout>"
    Then I shall receive the roomid unavailable
    #And I want to logout application with token
    Examples:
      | checkin    | checkout   | username | password |
      | 2025-07-03 | 2025-07-04 | Johndoe  | Johndoe1 |