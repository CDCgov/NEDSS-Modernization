@patient @patient-search
Feature: Patient Search

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient

  Scenario: I can find a patient by Patient ID using the short id
    Given patients are available for search
    And I would like to search for a patient using a short ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient by Patient ID using multiple short ids
    Given patients are available for search
    And I would like to search for a patient using multiple short IDs
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient by Patient ID using the local id
    Given patients are available for search
    And I would like to search for a patient using a local ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient by Patient ID using the local id and an id filter that equals the id
    Given patients are available for search
    And I would like to search for a patient using a local ID and a good equals id filter
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient by Patient ID using the local id and an id filter the contains the id
    Given patients are available for search
    And I would like to search for a patient using a local ID and a good contains id filter
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can't find a patient by Patient ID using a good local id and a bad id filter
    Given patients are available for search
    And I would like to search for a patient using a local ID and a bad id filter
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can filter search results with the patient's short ID
    Given I have a patient
    And the patient has the legal name "Joe" "Other"
    And I have another patient
    And the patient has the legal name "Joe" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that equals "Joe"
    And I would like to filter search results with the patient's short ID
    When I search for patients
    Then search result 1 has a "first name" of "Joe"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can filter search results with the patient's short ID and name
    Given I have a patient
    And the patient has the legal name "Joe" "Other"
    And I have another patient
    And the patient has the legal name "Joe" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that equals "Joe"
    And I would like to filter search results with the patient's short ID
    And I would like to filter search results with name "mit"
    When I search for patients
    Then search result 1 has a "first name" of "Joe"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can filter search results by last name
    Given I have a patient
    And the patient has the legal name "Joe" "Other"
    And I have another patient
    And the patient has the legal name "Joe" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that equals "Joe"
    And I would like to filter search results with name "mit"
    When I search for patients
    Then search result 1 has a "first name" of "Joe"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can filter search results by first name
    Given I have a patient
    And the patient has the legal name "Joe" "Smith"
    And I have another patient
    And the patient has the legal name "Joel" "Smith"
    And patients are available for search
    And I add the patient criteria for a last name that equals "Smith"
    And I would like to filter search results with name "oel"
    When I search for patients
    Then search result 1 has a "first name" of "Joel"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can filter search results by a non-existent name
    Given I have a patient
    And the patient has the legal name "Joe" "Smith"
    And I have another patient
    And the patient has the legal name "Ron" "Smith"
    And patients are available for search
    And I add the patient criteria for a last name that equals "Smith"
    And I would like to filter search results with name "Paul"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can find a patient by Patient ID using multiple local ids
    Given patients are available for search
    And I would like to search for a patient using multiple local IDs
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient by Patient ID using short and local ids
    Given patients are available for search
    And I would like to search for a patient using short and local IDs
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: Patient search does not fail when the Patient ID is blank (BUG CNFT1-2861)
    Given patients are available for search
    And I add the patient criteria for a "Patient ID" equal to ""
    When I search for patients
    Then the patient is in the search results

  Scenario: I can find patients with active record status
    Given I have another patient
    And the patient is inactive
    And patients are available for search
    And I would like patients that are "active"
    When I search for patients
    Then the search results have a patient with a "status" equal to "Active"

  Scenario: I can find patients with deleted record status
    Given I can "findInactive" any "patient"
    And the patient is inactive
    And patients are available for search
    And I would like patients that are "deleted"
    When I search for patients
    Then the search results have a patient with a "status" equal to "Inactive"

  Scenario: I cant search for deleted records without the correct permission
    Given I would like patients that are "deleted"
    When I search for patients
    Then I am not able to execute the search

  Scenario: I can find patients with superseded record status
    Given I can "findInactive" any "patient"
    And the patient is superseded
    And patients are available for search
    And I would like patients that are "superseded"
    When I search for patients
    Then the search results have a patient with a "status" equal to "SUPERCEDED"

  Scenario: I cant search for superseded records without the correct permission
    Given I would like patients that are "superseded"
    When I search for patients
    Then I am not able to execute the search


  Scenario Outline: When search criteria ends with a space, only the expected patients are returned
    Given the patient has a "<field>" of "<value>"
    And patients are available for search
    And I add the patient criteria for a "<field>" equal to "<value> "
    When I search for patients
    Then the patient is in the search results
    When I search for patients
    Then the patient is in the search results
    Examples:
      | field      | value        |
      | first name | First        |
      | last name  | Last         |
      | city       | City         |
      | address    | 1234 Address |

  Scenario: I can find the most relevant patient when searching by first name
    Given the patient has the legal name "Something" "Other"
    And I have another patient
    And the patient has the legal name "Jon" "Snow"
    And I have another patient
    And the patient has the legal name "John" "Little"
    And patients are available for search
    And I add the patient criteria for a "first name" equal to "John"
    When I search for patients
    Then search result 1 has a "first name" of "John"
    And search result 1 has a "last name" of "Little"
    And search result 2 has a "first name" of "Jon"
    And search result 2 has a "last name" of "Snow"

  Scenario: I can find the most relevant patient when searching by first name with soundex disabled
    Given the patient has the legal name "Something" "Other"
    And I have another patient
    And the patient has the legal name "Jon" "Snow"
    And I have another patient
    And the patient has the legal name "John" "Little"
    And patients are available for search
    And I add the patient criteria for a "first name" equal to "John"
    And I add the patient criteria for a "disable soundex" equal to "true"
    When I search for patients
    Then search result 1 has a "first name" of "John"
    And search result 1 has a "last name" of "Little"
    And there are 1 patient search results

  Scenario: I can find the most relevant patient when searching by last name
    Given the patient has the legal name "Something" "Other"
    And I have another patient
    And the patient has the legal name "Albert" "Smyth"
    And I have another patient
    And the patient has the legal name "Fatima" "Smith"
    And patients are available for search
    And I add the patient criteria for a "last name" equal to "Smith"
    When I search for patients
    Then search result 1 has a "first name" of "Fatima"
    And search result 1 has a "last name" of "Smith"
    And search result 2 has a "first name" of "Albert"
    And search result 2 has a "last name" of "Smyth"

  Scenario: I can find the most relevant patient when searching by last name with soundex disabled
    Given the patient has the legal name "Something" "Other"
    And I have another patient
    And the patient has the legal name "Albert" "Smyth"
    And I have another patient
    And the patient has the legal name "Fatima" "Smith"
    And patients are available for search
    And I add the patient criteria for a "last name" equal to "Smith"
    And I add the patient criteria for a "disable soundex" equal to "true"
    When I search for patients
    Then search result 1 has a "first name" of "Fatima"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can find the patient when searching for a hyphenated last name using a hyphen
    Given the patient has the legal name "Something" "Other-than"
    And patients are available for search
    And I add the patient criteria for a "last name" equal to "Other-than"
    When I search for patients
    Then search result 1 has a "last name" of "Other-than"

  Scenario: I can find the patient when searching for a hyphenated last name without using a hyphen
    Given the patient has the legal name "Something" "Other-than"
    And patients are available for search
    And I add the patient criteria for a "last name" equal to "Other than"
    When I search for patients
    Then search result 1 has a "last name" of "Other-than"

  Scenario: I can search for a Patient and find them by their legal name
    Given the patient has the legal name "Something" "Other" as of 01/01/2022
    And the patient has the legal name "This" "One" "Here", Jr. as of 11/13/2022
    And the patient has the Alias Name name "Al" "Lias"
    And patients are available for search
    When I search for patients
    Then the search results have a patient with a "legal first name" equal to "This"
    And the search results have a patient with a "legal middle name" equal to "One"
    And the search results have a patient with a "legal last name" equal to "Here"
    And the search results have a patient with a "legal name suffix" equal to "Jr."
    And the search results have a patient with an "Legal" first name of "Something"
    And the search results have a patient with an "Legal" last name of "Other"
    And the search results have a patient with an "Alias Name" first name of "Al"
    And the search results have a patient with an "Alias Name" last name of "Lias"

  Scenario: I can search for a Patient and find them by their legal name
    Given the patient has the "alias" name "Al" "Lias"
    And patients are available for search
    When I search for patients
    Then the search results have a patient without a "legal first name"
    And the search results have a patient without a "legal middle name"
    And the search results have a patient without a "legal last name"
    And the search results have a patient without a "legal name suffix"
    And the search results have a patient with a "first name" equal to "Al"
    And the search results have a patient with a "last name" equal to "Lias"

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

  Scenario: I can find the Patient with a short sex filter
    Given the patient has the gender <gender>
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I add the patient criteria for sex filter of "<code>"
    When I search for patients
    Then there is only one patient search result    

    Examples:
      | gender  | code |
      | Male    | M    |
      | Female  | F    |
      | Unknown | U    |

  Scenario: I can find the Patient with a long sex filter
    Given the patient has the gender <gender>
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I add the patient criteria for sex filter of "<gender>"
    When I search for patients
    Then there is only one patient search result    

    Examples:
      | gender  |
      | Male    |
      | Female  |
      | Unknown |   

  Scenario: I can search for a Patient when expanded sex filter matches gender
    Given the patient has the gender <gender>
    And patients are available for search
    And I add the patient criteria for a gender of <gender>
    And I add the patient criteria for sex filter of "<gender>"
    When I search for patients
    Then there is only one patient search result      

    Examples:
      | gender  |
      | Male    |
      | Female  |
      | Unknown |

  Scenario: I can search for a Patient when short sex filter matches gender
    Given the patient has the gender <gender>
    And patients are available for search
    And I add the patient criteria for a gender of <gender>
    And I add the patient criteria for sex filter of "<code>"
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
    And I add the patient criteria for sex filter of "u"
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
    And I add the patient criteria for sex filter of "F"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can search for a patient with a sex filter and name filter
    Given the patient has the gender Male
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I would like to filter search results with name "eva"
    And I add the patient criteria for sex filter of "M"
    When I search for patients
    And there are 1 patient search results

  Scenario: I can search for a patient with a sex filter that does not exist
    Given the patient has the gender Male
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    And I add the patient criteria for sex filter of "Q"
    When I search for patients
    Then there are 0 patient search results

  Scenario: BUG: CNFT1-1560 Patients with only a country code are searchable
    Given the patient has a "country code" of "+32"
    And the patient has a "first name" of "Eva"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Eva"
    When I search for patients
    Then the search results have a patient with a "first name" equal to "Eva"

  Scenario: BUG: CNFT1-1560 Patients with only an extension are searchable
    Given the patient has an "extension" of "3943"
    And the patient has a "first name" of "Liam"
    And patients are available for search
    And I add the patient criteria for an "first name" equal to "Liam"
    When I search for patients
    Then the search results have a patient with a "first name" equal to "Liam"

  Scenario: I can find a patient by last name using special characters without an error 
    Given the patient has the legal name "Sam" "Smith"
    And patients are available for search
    And I add the patient criteria for a "last name" equal to "Sm~th"
    When I search for patients
    Then there are 1 patient search results

  Scenario: I can find a patient by first name using special characters without an error 
    Given the patient has the legal name "Sam" "Smith"
    And patients are available for search
    And I add the patient criteria for a "first name" equal to "S~m"
    When I search for patients
    Then there are 1 patient search results

  Scenario: I can find a patient by address using special characters without an error 
    Given the patient has an "address" of "123 Way"
    And patients are available for search
    And I add the patient criteria for a "address" equal to "123 W~y"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can find a patient by email using special characters without an error 
    Given the patient has an "email address" of "email@test.com"
    And patients are available for search
    And I add the patient criteria for a "email address" equal to "email~test.com"
    When I search for patients
    Then there are 1 patient search results

  Scenario: I can find a patient by city using special characters without an error 
    Given the patient has an "city" of "acity"
    And patients are available for search
    And I add the patient criteria for a "city" equal to "a~ity"
    When I search for patients
    Then there are 0 patient search results
