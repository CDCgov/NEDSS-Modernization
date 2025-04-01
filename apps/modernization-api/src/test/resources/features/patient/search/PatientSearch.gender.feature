@patient @patient-search
Feature: Patient Search: Searching by gender

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient

  Scenario Outline: I can search for a Patient with a specified Gender
    Given the patient has the gender Male
    And I have another patient
    And the patient has the gender Female
    And I have another patient
    And the patient has the gender Unknown
    And patients are available for search
    And I add the patient criteria for a gender of <gender>
    When I search for patients
    Then there is only one patient search result
    And the search results have a patient with a "gender" equal to "<gender>"

    Examples:
      | gender  |
      | Male    |
      | Female  |
      | Unknown |


  Scenario: I can search for a Patient with an unknown Gender
    Given the patient has the gender Male
    And I have another patient
    And the patient has the gender Female
    And I have another patient
    And the patient has the gender Unknown
    And I have another patient
    And patients are available for search
    And I add the patient criteria for a gender of Unknown
    When I search for patients
    Then the search results have a patient with a "gender" equal to "Unknown"
    And there is only one patient search result

  Scenario Outline: I can find the Patient with a short sex filter
    Given the patient has the gender <gender>
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I would like to filter search results with a gender of "<code>"
    When I search for patients
    Then there is only one patient search result

    Examples:
      | gender  | code |
      | Male    | M    |
      | Female  | F    |
      | Unknown | U    |

  Scenario Outline: I can find the Patient with a long sex filter
    Given the patient has the gender <gender>
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I would like to filter search results with a gender of "<gender>"
    When I search for patients
    Then there is only one patient search result

    Examples:
      | gender  |
      | Male    |
      | Female  |
      | Unknown |

  Scenario Outline: I can search for a Patient when expanded sex filter matches gender
    Given the patient has the gender <gender>
    And patients are available for search
    And I add the patient criteria for a gender of <gender>
    And I would like to filter search results with a gender of "<gender>"
    When I search for patients
    Then there is only one patient search result

    Examples:
      | gender  |
      | Male    |
      | Female  |
      | Unknown |

  Scenario Outline: I can search for a Patient when short sex filter matches gender
    Given the patient has the gender <gender>
    And patients are available for search
    And I add the patient criteria for a gender of <gender>
    And I would like to filter search results with a gender of "<code>"
    When I search for patients
    Then there is only one patient search result

    Examples:
      | gender  | code |
      | Male    | M    |
      | Female  | F    |
      | Unknown | U    |

  Scenario: I can search for a Patient when sex filter matches gender
    Given the patient has the gender Male
    And I have another patient
    And the patient has the gender Female
    And I have another patient
    And the patient has the gender Unknown
    And I have another patient
    And patients are available for search
    And I add the patient criteria for a gender of Unknown
    And I would like to filter search results with a gender of "u"
    When I search for patients
    Then the search results have a patient with a "gender" equal to "Unknown"
    Then there is only one patient search result

  Scenario: I can search for a Patient when sex filter does not match gender
    Given the patient has the gender Male
    And I have another patient
    And the patient has the gender Female
    And I have another patient
    And the patient has the gender Unknown
    And I have another patient
    And patients are available for search
    And I add the patient criteria for a gender of Unknown
    And I would like to filter search results with a gender of "F"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can search for a patient with a sex filter and name filter
    Given the patient has the gender Male
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I would like to filter search results with name "eva"
    And I would like to filter search results with a gender of "M"
    When I search for patients
    And there are 1 patient search results

  Scenario: I can search for a patient with a sex filter that does not exist
    Given the patient has the gender Male
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I would like to filter search results with a gender of "Q"
    When I search for patients
    Then there are 0 patient search results
