@booking

Feature: Check booking unavailability with dates
  Scenario Outline: To check the booking unavailability based on dates
    Given I send a request to check booking unavailability based on "<checkin>", "<checkout>"
    Then I shall receive the roomid unavailable
    Examples:
      | checkin | checkout |
      | 2025-07-03  | 2025-07-04  |