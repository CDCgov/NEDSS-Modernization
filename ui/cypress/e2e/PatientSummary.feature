Feature: Patient Summary

  Scenario: I can view a patient summary
    Given a patient exists
    And I am a supervisor user
    Then I can view a patient's summary
