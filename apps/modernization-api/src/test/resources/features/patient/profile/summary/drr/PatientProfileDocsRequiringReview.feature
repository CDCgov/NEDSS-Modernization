@documents_requiring_review @documents @morbidity-report @lab-report
Feature: I can find documents requiring review for a particular patient

  Background:
    Given I am logged in
    And I have a patient
    And I can "find" any "patient"
    And the patient has a lab report
    And the lab report has not been processed
    And the patient has a Morbidity Report
    And the morbidity report has not been processed
    And the patient has an unprocessed document

  Scenario Outline: I can find documents requiring review for a patient
    Given I can "view" any "<permission>"
    When I search for documents requiring review for the patient
    Then the <document type> requiring review is returned
    Examples:
      | document type    | permission                 |
      | Morbidity Report | OBSERVATIONMORBIDITYREPORT |
      | document         | DOCUMENT                   |

  Scenario Outline: I can not find documents requiring review without the proper permission
    Given I can "view" any "<permission>"
    When I search for documents requiring review for the patient
    Then no <document type>s are returned
    Examples:
      | document type    | permission           |
      | Morbidity Report | OBSERVATIONLABREPORT |
      | document         | OBSERVATIONLABREPORT |

  Scenario: I can find documents requiring review for only the specific patient
    Given I can "view" any "DOCUMENT"
    And I can "view" any "OBSERVATIONMORBIDITYREPORT"
    And I can "view" any "OBSERVATIONLABREPORT"
    And I have another patient
    When I search for documents requiring review for the patient
    Then the patient has no documents requiring review

