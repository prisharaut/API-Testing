@booking

Feature: Validating booking unavailability with dates
  Scenario Outline: To validate the booking unavailability based on dates
    Given I send a request to check booking unavailability based on "<checkin>", "<checkout>"
    Then I shall receive the error response for incorrect booking details
    Examples:
      | checkin | checkout |
      | 1025-07-03  | 1025-07-04  |
      | 2025-07-04   |  |
      |         | 2025-07-04  |
      |         |  |