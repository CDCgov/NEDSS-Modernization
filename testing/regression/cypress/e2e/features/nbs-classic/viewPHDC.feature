Feature: View PHDCs

  Background:
    Given I import PHDC documents
    Given I am logged in as secure user and stay on classic

  Scenario: View an eCR
    Given I navigate to the DRSA Queue
    * I click on a patient's "Case Report"
    Then I can view the PHDC for "Minnie" "Mouse"
    * I can open the eCR in the eICR viewer

  Scenario: View a PHDC
    Given I search for a patient with a PHDC
    Then I can view the PHDC for "undisclosed" "undisclosed"
    * The "View eICR Document" button is not visible
