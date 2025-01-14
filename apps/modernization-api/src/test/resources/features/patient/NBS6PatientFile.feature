Feature: NBS6 Patient File redirection

  Background:
    Given I am logged in
    And I have a patient

  Scenario: I want to view a patient in an NBS6 patient file
    When I navigate to the NBS6 Patient file
    Then NBS6 is prepared to show the Patient file
    And I am redirected to the NBS6 Patient file
