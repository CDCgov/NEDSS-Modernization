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
