Feature: Page Builder - User can view Preview Page here.

  Background:
    Given I am logged in as "superuser" and password "@test"
    When User navigates to Preview Page and views the Preview Page

  Scenario Outline: Verify the data elements in the preview page header buttons linked with functionalities
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Delete draft     | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

  Scenario Outline: Verify the data elements in the preview page header buttons when page status is draft
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Delete draft     | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

