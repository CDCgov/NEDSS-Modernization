@documents_requiring_review @documents
Feature: I can find documents requiring review for a particular patient

  Background:
    Given I am logged in
    And I have a patient
    And I can "find" any "patient"

  Scenario Outline: I can find documents requiring review for a patient
    Given I can "view" any "<permission>"
    And the patient has an unprocessed <document type>
    When I search for documents requiring review for the patient
    Then the <document type> requiring review is returned
    Examples:
      | document type | permission |
#      | morbidity report | OBSERVATIONMORBIDITYREPORT |
      | document      | DOCUMENT   |

  Scenario Outline: I can not find documents requiring review without the proper permission
    Given I can "view" any "<permission>"
    And the patient has a lab report
    And the lab report has not been processed
    And the patient has an unprocessed morbidity report
    And the patient has an unprocessed document
    When I search for documents requiring review for the patient
    Then no <document type> are returned
    Examples:
      | document type | permission           |
#      | morbidity report | OBSERVATIONLABREPORT       |
      | document      | OBSERVATIONLABREPORT |

