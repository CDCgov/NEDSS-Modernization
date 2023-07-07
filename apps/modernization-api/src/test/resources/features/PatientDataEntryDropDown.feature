## Created by jamesbarrows at 5/19/23
@patient_entry_drop_downs
Feature: Dropdown element retrieval for patient data entry
  AS A Data Entry user
  I WANT TO Be able to see dropdown elements on the data entry modals
  SO THAT I CAN use those options to complete the patient profile details.

  Background:
    Given A user exists
    And I have authenticated as a user
    And I have the authorities: "ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"

  Scenario: I can retrieve Name Suffixes
    When I want to retrieve "Name Suffix"
    Then I get these key-value pairs:
      | Key | Value           |
      | ESQ | Esquire         |
      | II  | II / The Second |
      | III | III / The Third |
      | IV  | IV / The Fourth |
      | JR  | Jr.             |
      | SR  | Sr.             |
      | V   | V / The Fifth   |

  Scenario: I can retrieve Genders
    When I want to retrieve "Gender"
    Then I get these key-value pairs:
      | Key | Value   |
      | M   | Male    |
      | F   | Female  |
      | U   | Unknown |

  Scenario: I can retrieve Yes/No/Unknown
    When I want to retrieve "Yes/No/Unknown"
    Then I get these key-value pairs:
      | Key     | Value   |
      | YES     | Yes     |
      | NO      | No      |
      | UNKNOWN | Unknown |

  Scenario: I can retrieve Name Type
    When I want to retrieve "Name Type"
    Then I get these key-value pairs:
      | Key | Value                  |
      | A   | Artist/Stage Name      |
      | AD  | Adopted Name           |
      | AL  | Alias Name             |
      | BR  | Name at Birth          |
      | C   | License                |
      | I   | Indigenous/Tribal      |
      | L   | Legal                  |
      | M   | Maiden Name            |
      | MO  | Mother's Name          |
      | P   | Name of Partner/Spouse |
      | R   | Religious             |
      | S   | Coded Pseudo           |

  Scenario: I can retrieve Name Prefix
    When I want to retrieve "Name Prefix"
    Then I get these key-value pairs:
      | Key  | Value      |
      | BRO  | Brother    |
      | BSHP | Bishop     |
      | CARD | Cardinal   |
      | DR   | Doctor/Dr. |
      | FATH | Father     |
      | HON  | Honorable  |
      | MISS | Miss       |
      | MON  | Monsignor  |
      | MOTH | Mother     |
      | MR   | Mr.        |
      | MRS  | Mrs.       |
      | MS   | Ms.        |
      | PAST | Pastor     |
      | PROF | Professor  |
      | RAB  | Rabbi      |
      | REV  | Reverend   |
      | SIS  | Sister     |
      | SWM  | Swami      |


  Scenario: I can retrieve Degree
    When I want to retrieve "Degree"
    Then I get these key-value pairs:
      | Key | Value |
      | DO  | DO    |
      | DRN | DRN   |
      | DVM | DVM   |
      | JD  | JD    |
      | LLB | LLB   |
      | LPN | LPN   |
      | MA  | MA    |
      | MBA | MBA   |
      | MD  | MD    |
      | MED | MED   |
      | MPH | MPH   |
      | MS  | MS    |
      | MSN | MSN   |
      | NP  | NP    |
      | PA  | PA    |
      | PHD | PhD   |
      | RN  | RN    |

  Scenario: I can retrieve Address Types
    When I want to retrieve "Address Type"
    Then I get these key-value pairs:
      | Key  | Value                                        |
      | O    | Office                                       |
      | PF   | Prison - Federal                             |
      | PLOC | Jail                                         |
      | PNS  | Prison/Correctional Facility - not specified |
      | PS   | Prison - State                               |
      | RH   | Registry Home                                |
      | RTF  | Residential treatment facility               |
      | SHLT | Shelter - unspecified                        |
      | T    | Transitional Facility                        |
      | U    | Unknown                                      |

  Scenario: I can retrieve counties
    When I want to retrieve counties from state "Alabama"
    Then I get these counties:
      | 01001 | 2.16.840.1.113883    | Health Level Seven        | Autauga County     | Autauga, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 3      |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01003 | 2.16.840.1.113883    | Health Level Seven        | Baldwin County     | Baldwin, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 4      |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01005 | 2.16.840.1.113883    | Health Level Seven        | Barbour County     | Barbour, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 5      |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01007 | 2.16.840.1.113883    | Health Level Seven        | Bibb County        | Bibb, AL         | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 6      |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01009 | 2.16.840.1.113883    | Health Level Seven        | Blount County      | Blount, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 7      |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01011 | 2.16.840.1.113883    | Health Level Seven        | Bullock County     | Bullock, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 8      |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01013 | 2.16.840.1.113883    | Health Level Seven        | Butler County      | Butler, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 9      |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01015 | 2.16.840.1.113883    | Health Level Seven        | Calhoun County     | Calhoun, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 10     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01017 | 2.16.840.1.113883    | Health Level Seven        | Chambers County    | Chambers, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 11     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01019 | 2.16.840.1.113883    | Health Level Seven        | Cherokee County    | Cherokee, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 12     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01021 | 2.16.840.1.113883    | Health Level Seven        | Chilton County     | Chilton, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 13     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01023 | 2.16.840.1.113883    | Health Level Seven        | Choctaw County     | Choctaw, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 14     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01025 | 2.16.840.1.113883    | Health Level Seven        | Clarke County      | Clarke, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 15     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01027 | 2.16.840.1.113883    | Health Level Seven        | Clay County        | Clay, AL         | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 16     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01029 | 2.16.840.1.113883    | Health Level Seven        | Cleburne County    | Cleburne, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 17     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01031 | 2.16.840.1.113883    | Health Level Seven        | Coffee County      | Coffee, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 18     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01033 | 2.16.840.1.113883    | Health Level Seven        | Colbert County     | Colbert, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 19     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01035 | 2.16.840.1.113883    | Health Level Seven        | Conecuh County     | Conecuh, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 20     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01037 | 2.16.840.1.113883    | Health Level Seven        | Coosa County       | Coosa, AL        | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 21     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01039 | 2.16.840.1.113883    | Health Level Seven        | Covington County   | Covington, AL    | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 22     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01041 | 2.16.840.1.113883    | Health Level Seven        | Crenshaw County    | Crenshaw, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 23     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01043 | 2.16.840.1.113883    | Health Level Seven        | Cullman County     | Cullman, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 24     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01045 | 2.16.840.1.113883    | Health Level Seven        | Dale County        | Dale, AL         | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 25     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01047 | 2.16.840.1.113883    | Health Level Seven        | Dallas County      | Dallas, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 26     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01049 | 2.16.840.1.113883    | Health Level Seven        | De Kalb County     | De Kalb, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 27     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01051 | 2.16.840.1.113883    | Health Level Seven        | Elmore County      | Elmore, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 28     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01053 | 2.16.840.1.113883    | Health Level Seven        | Escambia County    | Escambia, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 29     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01055 | 2.16.840.1.113883    | Health Level Seven        | Etowah County      | Etowah, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 30     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01057 | 2.16.840.1.113883    | Health Level Seven        | Fayette County     | Fayette, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 31     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01059 | 2.16.840.1.113883    | Health Level Seven        | Franklin County    | Franklin, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 32     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01061 | 2.16.840.1.113883    | Health Level Seven        | Geneva County      | Geneva, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 33     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01063 | 2.16.840.1.113883    | Health Level Seven        | Greene County      | Greene, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 34     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01065 | 2.16.840.1.113883    | Health Level Seven        | Hale County        | Hale, AL         | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 35     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01067 | 2.16.840.1.113883    | Health Level Seven        | Henry County       | Henry, AL        | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 36     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01069 | 2.16.840.1.113883    | Health Level Seven        | Houston County     | Houston, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 37     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01071 | 2.16.840.1.113883    | Health Level Seven        | Jackson County     | Jackson, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 38     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01073 | 2.16.840.1.113883    | Health Level Seven        | Jefferson County   | Jefferson, AL    | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 39     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01075 | 2.16.840.1.113883    | Health Level Seven        | Lamar County       | Lamar, AL        | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 40     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01077 | 2.16.840.1.113883    | Health Level Seven        | Lauderdale County  | Lauderdale, AL   | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 41     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01079 | 2.16.840.1.113883    | Health Level Seven        | Lawrence County    | Lawrence, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 42     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01081 | 2.16.840.1.113883    | Health Level Seven        | Lee County         | Lee, AL          | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 43     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01083 | 2.16.840.1.113883    | Health Level Seven        | Limestone County   | Limestone, AL    | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 44     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01085 | 2.16.840.1.113883    | Health Level Seven        | Lowndes County     | Lowndes, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 45     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01087 | 2.16.840.1.113883    | Health Level Seven        | Macon County       | Macon, AL        | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 46     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01089 | 2.16.840.1.113883    | Health Level Seven        | Madison County     | Madison, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 47     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01091 | 2.16.840.1.113883    | Health Level Seven        | Marengo County     | Marengo, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 48     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01093 | 2.16.840.1.113883    | Health Level Seven        | Marion County      | Marion, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 49     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01095 | 2.16.840.1.113883    | Health Level Seven        | Marshall County    | Marshall, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 50     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01097 | 2.16.840.1.113883    | Health Level Seven        | Mobile County      | Mobile, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 51     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01099 | 2.16.840.1.113883    | Health Level Seven        | Monroe County      | Monroe, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 52     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01101 | 2.16.840.1.113883    | Health Level Seven        | Montgomery County  | Montgomery, AL   | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 53     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01103 | 2.16.840.1.113883    | Health Level Seven        | Morgan County      | Morgan, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 54     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01105 | 2.16.840.1.113883    | Health Level Seven        | Perry County       | Perry, AL        | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 55     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01107 | 2.16.840.1.113883    | Health Level Seven        | Pickens County     | Pickens, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 56     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01109 | 2.16.840.1.113883    | Health Level Seven        | Pike County        | Pike, AL         | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 57     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01111 | 2.16.840.1.113883    | Health Level Seven        | Randolph County    | Randolph, AL     | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 58     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01113 | 2.16.840.1.113883    | Health Level Seven        | Russell County     | Russell, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 59     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01115 | 2.16.840.1.113883    | Health Level Seven        | Saint Clair County | Saint Clair, AL  | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 60     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01117 | 2.16.840.1.113883    | Health Level Seven        | Shelby County      | Shelby, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 61     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01119 | 2.16.840.1.113883    | Health Level Seven        | Sumter County      | Sumter, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 62     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01121 | 2.16.840.1.113883    | Health Level Seven        | Talladega County   | Talladega, AL    | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 63     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01123 | 2.16.840.1.113883    | Health Level Seven        | Tallapoosa County  | Tallapoosa, AL   | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 64     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01125 | 2.16.840.1.113883    | Health Level Seven        | Tuscaloosa County  | Tuscaloosa, AL   | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 65     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01127 | 2.16.840.1.113883    | Health Level Seven        | Walker County      | Walker, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 66     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01129 | 2.16.840.1.113883    | Health Level Seven        | Washington County  | Washington, AL   | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 67     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01131 | 2.16.840.1.113883    | Health Level Seven        | Wilcox County      | Wilcox, AL       | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 68     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County) |
      | 01133 | 2.16.840.1.113883    | Health Level Seven        | Winston County     | Winston, AL      | 1900-01-01 00:00:00.000 | 2010-01-01 00:00:00.000 |             | 2              | N               | 01         | A        | 2003-06-19 00:00:00.000 | COUNTY_CCD | 1      | 69     |                 | 2.16.840.1.113883.6.93 | FIPS 6-4 (County)|

   