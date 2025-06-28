@booking

Feature: Filter booking summary with roomid

  Scenario Outline: To filter the booking summary with roomid
    Given I send a request to Get booking summary based on roomid "<roomid>"
    Then I shall receive the booking summary successfully
    Examples:
      | roomid |
      | 1  |