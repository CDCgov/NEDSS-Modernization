Feature: I can preview home page locally defined fields

  Background: 
    Given I am a NEDSS admin user
    And Home Page locally defined fields exist

  Scenario: I can preview home page locally defined fields
    Given I am a NEDSS admin user
    Then I can see the preview the home page locally defined fields

  Scenario: I can delete home page locally defined fields
    Given I am a NEDSS admin user
    Then I can delete home page locally defined fields
