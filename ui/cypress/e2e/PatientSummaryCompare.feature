Feature: Patient Compare Summary

  Scenario: I can compare patients before merging
    Given A duplicated patient exists
    And I am a registry manager user
    When I compare merge candidates
    Then I can view both patient summaries
