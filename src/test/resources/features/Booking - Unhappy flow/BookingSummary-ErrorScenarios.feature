@booking

Feature: Validating the Filter booking summary

  Scenario Outline: To validate filter the booking summary invalid roomid
    Given I send a request to Get booking summary based on roomid "<roomid>"
    Then I shall receive the error response for incorrect data
    Examples:
      | roomid |
      | &+  |
      | abc  |
      | 999199999  |
      |   |






