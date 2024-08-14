Feature: Patient Search by Id

  Background:
    Given I am logged in as secure user

  Scenario: ID - Search by Account Number
    When I navigate to id section
    Then I search by id type as "Account number" and id as "2776129"
    Then I should see Results with the "account number" as "2776129"

  Scenario: ID - Alternate person number
    When I navigate to id section
    Then I search by id type as "Alternate person number" and id as "201458965"
    Then I should see Results with the "ALTERNATE PERSON NUMBER" as "201458965"

  Scenario: ID - CHIP Identification number
    When I navigate to id section
    Then I search by id type as "CHIP Identification number" and id as "1"
    Then I should see Results with the "CHIP IDENTIFICATION NUMBER" as "1"

  Scenario: ID - Driver's license number
    When I navigate to id section
    Then I search by id type as "Driver's license number" and id as "12365789"
    Then I should see Results with the "DRIVER'S LICENSE NUMBER" as "12365789"

  Scenario: ID - Immunization Registry ID
    When I navigate to id section
    Then I search by id type as "Immunization Registry ID" and id as "859674"
    Then I should see Results with the "IMMUNIZATION REGISTRY ID" as "859674"

  Scenario: ID - Medicaid number
    When I navigate to id section
    Then I search by id type as "Medicaid number" and id as "859674"
    Then I should see Results with the "MEDICAID NUMBER" as "859674"

  Scenario: ID - Medical record number
    When I navigate to id section
    Then I search by id type as "Medical record number" and id as "123456"
    Then I should see Results with the "MEDICAL RECORD NUMBER" as "123456"

  Scenario: ID - Medicare number
    When I navigate to id section
    Then I search by id type as "Medicare number" and id as "859674"
    Then I should see Results with the "MEDICARE NUMBER" as "859674"

  Scenario: ID - Mother's identifier
    When I navigate to id section
    Then I search by id type as "Mother's identifier" and id as "859674"
    Then I should see Results with the "MOTHER'S IDENTIFIER" as "859674"

  Scenario: ID - National unique individual identifier
    When I navigate to id section
    Then I search by id type as "National unique individual identifier" and id as "3"
    Then I should see Results with the "NATIONAL UNIQUE INDIVIDUAL IDENTIFIER" as "3"

  Scenario: ID - Other
    When I navigate to id section
    Then I search by id type as "Other" and id as "123456"
    Then I should see Results with the "OTHER" as "123456"

  Scenario: ID - Partner Services (Legacy) Patient Number
    When I navigate to id section
    Then I search by id type as "Partner Services Patient Number" and id as "10"
    Then I should see Results with the "PATIENT EXTERNAL IDENTIFIER" as "10"

  Scenario: ID - Patient External Identifier
    When I navigate to id section
    Then I search by id type as "Patient External Identifier" and id as "1"
    Then I should see Results with the "PARTNER SERVICES PATIENT NUMBER" as "1"

  Scenario: ID - Patient Internal Identifier
    When I navigate to id section
    Then I search by id type as "Patient Internal Identifier" and id as "1"
    Then I should see Results with the "PATIENT INTERNAL IDENTIFIER" as "1"

  Scenario: ID - Person number
    When I navigate to id section
    Then I search by id type as "Person number" and id as "89"
    Then I should see Results with the "PERSON NUMBER" as "89"

  Scenario: ID - Prison identification number
    When I navigate to id section
    Then I search by id type as "Prison identification number" and id as "NA"
    Then I should see Results with the "PRISON IDENTIFICATION NUMBER" as "NA"

  Scenario: ID - Ryan White identifier
    When I navigate to id section
    Then I search by id type as "Ryan White identifier" and id as "y"
    Then I should see Results with the "RYAN WHITE IDENTIFIER" as "y"

  Scenario: ID - Social Security
    When I navigate to id section
    Then I search by id type as "Social Security" and id as "33"
    Then I should see Results with the "SOCIAL SECURITY" as "33"

  Scenario: ID - VISA/Passport
    When I navigate to id section
    Then I search by id type as "VISA/Passport" and id as "2"
    Then I should see Results with the "VISA/PASSPORT" as "2"

  Scenario: ID - WIC identifier
    When I navigate to id section
    Then I search by id type as "WIC identifier" and id as "1"
    Then I should see Results with the "VISA/PASSPORT" as "1"
