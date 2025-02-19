@patient-search @patient-search-results
Feature: Patient Search Result Addresses

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient

  Scenario: I can search for a Patient using an email address
    Given the patient has an email address of "emailaddress@mail.com"
    And I have another patient
    And the patient has an email address of "other@mail.com"
    And patients are available for search
    And I add the patient criteria for an "email address" equal to "emailaddress@mail.com"
    When I search for patients
    Then the search results have a patient with an "email address" equal to "emailaddress@mail.com"

  Scenario: I can search for a Patient using an email address and an email filter
    Given the patient has an email address of "emailaddress@mail.com"
    And I have another patient
    And the patient has an email address of "other@mail.com"
    And patients are available for search
    And I add the patient criteria for an "email address" equal to "emailaddress@mail.com"
    And I would like to filter search results with email "address"
    When I search for patients
    Then the search results have a patient with an "email address" equal to "emailaddress@mail.com"

  Scenario: I can search for a Patient using an email address and an exact match email filter
    Given the patient has an email address of "emailaddress@mail.com"
    And I have another patient
    And the patient has an email address of "other@mail.com"
    And patients are available for search
    And I add the patient criteria for an "email address" equal to "emailaddress@mail.com"
    And I would like to filter search results with email "emailaddress@mail.com"
    When I search for patients
    Then the search results have a patient with an "email address" equal to "emailaddress@mail.com"

  Scenario: I can search for a Patient using an email address and a non-matching email filter
    Given the patient has an email address of "emailaddress@mail.com"
    And I have another patient
    And the patient has an email address of "other@mail.com"
    And patients are available for search
    And I add the patient criteria for an "email address" equal to "emailaddress@mail.com"
    And I would like to filter search results with email "QQQ"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can search for a Patient using an email address and two filters
    Given the patient has an email address of "emailaddress@mail.com"
    And the patient has the gender Unknown
    And I have another patient
    And the patient has an email address of "other@mail.com"
    And patients are available for search
    And I add the patient criteria for an "email address" equal to "emailaddress@mail.com"
    And I add the patient criteria for sex filter of "u"
    And I would like to filter search results with email "address"
    When I search for patients
    Then the search results have a patient with an "email address" equal to "emailaddress@mail.com"

  Scenario: I can search for a Patient using an email address from a non Email Address type
    Given the patient has the Beeper - Home email address of "beeper@pho.ne"
    And I have another patient
    And the patient has an email address of "other@mail.com"
    And patients are available for search
    And I add the patient criteria for an "email address" equal to "beeper@pho.ne"
    When I search for patients
    Then the search results have a patient with an "email address" equal to "beeper@pho.ne"

  Scenario: Patient Search Results contain email addresses order by as of date
    Given the patient has the Beeper - Home email address of "email-three@ema.il" as of 05/13/1997
    And the patient has the Email Address - Home email address of "email-one@ema.il" as of 11/07/2023
    And the patient has the Email Address - Primary Work Place email address of "email-two@ema.il" as of 07/11/2023
    And patients are available for search
    And I would like to search for a patient using a local ID
    When I search for patients
    Then the search results have a patient with the 1st "email address" equal to "email-one@ema.il"
    And the search results have a patient with the 2nd "email address" equal to "email-two@ema.il"
    And the search results have a patient with the 3rd "email address" equal to "email-three@ema.il"
