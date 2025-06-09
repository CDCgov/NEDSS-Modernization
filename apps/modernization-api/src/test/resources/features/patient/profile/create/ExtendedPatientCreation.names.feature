@patient_extended_create
  Feature: Creation of Patients with extended name data

    Background:
      Given I am logged into NBS
      And I can "add" any "patient"
      And I can "view" any "patient"

    Scenario: I can create a patient with a legal name
      Given I am entering a legal name as of 07/19/2017
      And I enter the prefix Honorable on the current name
      And I enter the first name "Harold" on the current name
      And I enter the last name "Stone" on the current name
      And I enter the degree LLD on the current name
      And the name is included with the extended patient data
      When I create a patient with extended data
      Then I view the patient's name demographics
      And the patient file name demographics as of 07/19/2017 contains the prefix Honorable
      And the patient file name demographics as of 07/19/2017 contains the first name "Harold"
      And the patient file name demographics as of 07/19/2017 contains the last name "Stone"
      And the patient file name demographics as of 07/19/2017 contains the degree LLD

    Scenario: I can create a patient with a single name
      Given I am entering a Artist/Stage Name name as of 02/17/1989
      And I enter the first name "Bill" on the current name
      And I enter the middle name "S" on the current name
      And I enter the last name "Preston" on the current name
      And I enter the suffix Esquire on the current name
      And the name is included with the extended patient data
      When I create a patient with extended data
      Then I view the patient's name demographics
      And the patient file name demographics as of 02/17/1989 contains the first name "Bill"
      And the patient file name demographics as of 02/17/1989 contains the middle name "S"
      And the patient file name demographics as of 02/17/1989 contains the last name "Preston"
      And the patient file name demographics as of 02/17/1989 contains the suffix Esquire

    Scenario: I can create a patient with a long name
      Given I am entering a Artist/Stage Name name as of 04/01/1985
      And I enter the first name "Mary" on the current name
      And I enter the middle name "Elizabeth" on the current name
      And I enter the second middle name "Jennifer" on the current name
      And I enter the last name "Rachel" on the current name
      And I enter the second last name "Abergavenny" on the current name
      And the name is included with the extended patient data
      When I create a patient with extended data
      Then I view the patient's name demographics
      And the patient file name demographics as of 04/01/1985 contains the first name "Mary"
      And the patient file name demographics as of 04/01/1985 contains the middle name "Elizabeth"
      And the patient file name demographics as of 04/01/1985 contains the second middle name "Jennifer"
      And the patient file name demographics as of 04/01/1985 contains the last name "Rachel"
      And the patient file name demographics as of 04/01/1985 contains the second last name "Abergavenny"

    Scenario: I can create a patient with multiple names
      Given I am entering a Name at Birth name as of 05/27/1975
      And I enter the first name "Marc" on the current name
      And I enter the last name "Spector" on the current name
      And the name is included with the extended patient data
      And I am entering a Alias Name name as of 03/09/1987
      And I enter the first name "Steven" on the current name
      And I enter the last name "Grant" on the current name
      And the name is included with the extended patient data
      When I create a patient with extended data
      Then I view the patient's name demographics
      And the patient file name demographics as of 05/27/1975 contains the first name "Marc"
      And the patient file name demographics as of 05/27/1975 contains the last name "Spector"
      And the patient file name demographics as of 03/09/1987 contains the first name "Steven"
      And the patient file name demographics as of 03/09/1987 contains the last name "Grant"
