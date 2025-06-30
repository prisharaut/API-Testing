@booking

Feature: Filter booking with roomid
  Background:
    Given I want to login to application with "admin", "password" and received token
  Scenario Outline: To filter the booking details with roomid
    Given I send a request to filter details based on roomid "<roomid>"
    Then I shall receive the booking for roomid successfully
    Examples:
      | roomid |
      | 1  |