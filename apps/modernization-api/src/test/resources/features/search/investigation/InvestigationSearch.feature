@investigation_search
Feature: Investigation search

  Background:
    Given I am logged in
    And I can "FIND" any "PATIENT"
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "ARBO" within all jurisdictions
    And I have a patient
    And the patient is a subject of an investigation
    And investigations are available for search
    And I have another patient
    And the patient is a subject of an investigation

  Scenario: I can search for Investigations for a specific patient
    Given investigations are available for search
    And I want to find investigations for the patient
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result
    And the Investigation search results contain the patient short id

  Scenario: I cannot find Investigations for a patient if the patient is not a subject of any Investigations
    Given I have another patient
    And investigations are available for search
    And I want to find investigations for the patient
    When I search for investigations
    Then there are no investigation search results available

  Scenario: I can search for Investigations created by a specific user
    Given the "investigation-creator" user exists
    And the investigation was created by investigation-creator on 01/27/2011
    And the investigation is available for search
    And I want to find investigations created by investigation-creator
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations created on a specific day
    Given the "investigation-creator" user exists
    And the investigation was created by investigation-creator on 01/27/2011
    And the investigation is available for search
    And I want to find investigations created on 01/27/2011
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations updated by a specific user
    Given the "investigation-updater" user exists
    And the investigation was updated by investigation-updater on 02/13/2013
    And the investigation is available for search
    And I want to find investigations updated by investigation-updater
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations updated on a specific day
    Given the "investigation-updater" user exists
    And the investigation was updated by investigation-updater on 02/13/2013
    And the investigation is available for search
    And I want to find investigations updated on 02/13/2013
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations within a Program Area
    Given the investigation is for ARBO within Dekalb County
    And the investigation is available for search
    And I want to find investigations within the ARBO Program Area
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I cannot find Investigations when investigations are not within the Program Area
    Given the investigation is for ARBO within Dekalb County
    And the investigation is available for search
    And I want to find investigations within the BMIRD Program Area
    When I search for investigations
    Then there are no investigation search results available

  Scenario: I can search for Investigations within a Jurisdiction
    Given the investigation is for ARBO within Dekalb County
    And the investigation is available for search
    And I want to find investigations within the Dekalb County Jurisdiction
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I cannot find Investigations when investigations are not within the Jurisdiction
    Given the investigation is for ARBO within Dekalb County
    And the investigation is available for search
    And I want to find investigations within the Gwinnett County Jurisdiction
    When I search for investigations
    Then there are no investigation search results available

  Scenario: I can search for Investigations for patients that are pregnant
    Given the investigation is for a patient that is pregnant
    And the investigation is available for search
    And I want to find investigations for patients that are pregnant
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations for patients that are not pregnant
    Given the investigation is for a patient that is not pregnant
    And the investigation is available for search
    And I want to find investigations for patients that are not pregnant
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find Investigations for patients that do not know if they are pregnant
    Given the investigation is for a patient that does not know if they are pregnant
    And the investigation is available for search
    And I want to find investigations for patients that do not know if they are pregnant
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations for a specific condition
    Given the investigation is for the Toscana Virus condition
    And the investigation is available for search
    And I want to find investigations for the Toscana Virus condition
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I cannot find Investigations for conditions not under investigation
    Given the investigation is for the Toscana Virus condition
    And the investigation is available for search
    And I want to find investigations for the Other Arbovirus condition
    When I search for investigations
    Then there are no investigation search results available

  Scenario Outline: I can find an investigation by different status fields
    Given the investigation has a <status> status of <value>
    And the investigation is available for search
    And I want to find investigations with a <status> status of <value>
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

    Examples:
      | status       | value                  |
      | processing   | Awaiting Interview     |
      | processing   | Field Follow-up        |
      | processing   | No Follow-up           |
      | processing   | Open Case              |
      | processing   | Surveillance Follow-up |
      | notification | Approved               |
      | notification | Completed              |
      | notification | Message Failed         |
      | notification | Pending Approval       |
      | notification | Rejected               |
      | case         | Confirmed              |
      | case         | Not a Case             |
      | case         | Probable               |
      | case         | Suspect                |
      | case         | Unknown                |

  Scenario Outline: I can find an investigation by different status fields
    Given the investigation is available for search
    And I want to find investigations with a <status> status of UNASSIGNED
    When I search for investigations
    Then the Investigation search results contain the Investigation

    Examples:
      | status       |
      | processing   |
      | notification |
      | case         |

  Scenario: I can find investigations started on a specific day
    Given the investigation was started on 08/19/2011
    And the investigation is available for search
    And I want to find investigations started on 08/19/2011
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find open investigations
    Given the investigation was closed on 09/17/2018
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want to find open investigations
    When I search for investigations
    Then the Investigation search results contain the Investigation

  Scenario: I can find closed investigations
    Given the investigation was closed on 09/17/2018
    And the investigation is available for search
    And I want to find closed investigations
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations closed on a specific day
    Given the investigation was closed on 09/17/2018
    And the investigation is available for search
    And I want to find investigations closed on 09/17/2018
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations reported on a specific day
    Given the investigation was reported on 08/19/2011
    And the investigation is available for search
    And I want to find investigations reported on 08/19/2011
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations notified on a specific day
    Given the investigation has a notification Approved as of 08/29/2001
    And the investigation is available for search
    And I want to find investigations notified on 08/29/2001
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations for a specific investigation
    Given the investigation is available for search
    And I want to find the investigation by id
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations for a specific notification
    Given the investigation has a notification Approved as of 08/29/2001
    And the investigation is available for search
    And I want to find investigations for the notification
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations related to Active Bacterial Core Surveillance cases
    Given the investigation is related to ABCs Case "1013673"
    And the investigation is available for search
    And I want to find investigations for the ABCs Case "1013673"
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations related to State cases
    Given the investigation is related to State Case "587641"
    And the investigation is available for search
    And I want to find investigations for the State Case "587641"
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can find investigations related to County cases
    Given the investigation is related to County Case "883269"
    And the investigation is available for search
    And I want to find investigations for the County Case "883269"
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations investigated by a specific provider
    Given the patient has a lab report
    And there is a provider named "Nancy" "Wheeler"
    And the investigation was investigated by the provider
    And the investigation is available for search
    And I want to find investigations investigated by the provider
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations reported by a specific facility
    Given the patient has a lab report
    And the investigation was reported by the Emory University Hospital facility
    And the investigation is available for search
    And I want to find investigations reported by the Emory University Hospital facility
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations reported by a specific facility using the new api
    Given the patient has a lab report
    And the investigation was reported by the Emory University Hospital facility
    And the investigation is available for search
    And I want to find investigations reported by the Emory University Hospital facility using the new api
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations reported by a specific provider
    Given there is a provider named "Robin" "Buckley"
    And the investigation was reported by the provider
    And the investigation is available for search
    And I want to find investigations reported by the provider
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations reported by a specific provider using the new api
    Given there is a provider named "Robin" "Buckley"
    And the investigation was reported by the provider
    And the investigation is available for search
    And I want to find investigations reported by the provider using the new api
    When I search for investigations
    Then the Investigation search results contain the Investigation
    And there is only one investigation search result

  Scenario: I can search for Investigations related to an outbreak
      Given "Peanuts at Fratelli's Restaurant" caused an outbreak
      And the investigation is related to the Peanuts at Fratelli's Restaurant outbreak
      And the investigation is available for search
      And I want to find investigations related to the Peanuts at Fratelli's Restaurant outbreak
      When I search for investigations
      Then the Investigation search results contain the Investigation
      And there is only one investigation search result
