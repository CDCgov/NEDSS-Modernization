import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicOrganizationPage from "../../e2e/pages/nbs-classic/organization.page";
import editOrganizationPage from "../../e2e/pages/nbs-classic/editOrganization.page";
import { maps } from "../organizationValueMaps";
import { clickSubmitButton, clickAddButton } from "../../e2e/pages/nbs-classic/utils";

// Navigation and search steps
When("I navigate to classic organization Search pane", () => {
  classicOrganizationPage.navigateToClassicOrganizationSearchPane()
});

When("I enter organization name in input text field {string}", (text) => {
  classicOrganizationPage.enterOrganizationNameInSearch(text)
});

When("I enter a unique organization name in the name field", () => {
  classicOrganizationPage.enterUniqueOrganizationNameInSearch()
});

When("I enter the same organization name in the Search Pane", () => {
  classicOrganizationPage.enterSameOrganizationNameInSearchPane();
});

When("I save the number of search results", () => {
  classicOrganizationPage.saveSearchResultsCount();
});

When("I enter organization address in input text field {string}", (text) => {
  classicOrganizationPage.enterOrganizationAddressInSearch(text)
});

When("I click on the Submit button", () => {
  clickSubmitButton()
});

// Add organization steps

When("I enter a random ID Value", () => {
  editOrganizationPage.enterRandomIdValue()
});


When("I select the role {string}", (roleName) => {
  editOrganizationPage.selectRole(roleName)
});

When ("I click the Add button", () => {
  clickAddButton()
});

When ("I select a random ID Type", () => {
  editOrganizationPage.selectRandomIdType()
});

When ("I select a random Assigning Authority", () => {
  editOrganizationPage.selectRandomAssigningAuthority()
});

When ("I click the Add Identification button", () => {
  editOrganizationPage.clickAddIdentificationButton()
});

When("I add two new ID records with different ID Types and Assigning Authorities", () => {
  classicOrganizationPage.addTwoNewIdRecords();
});

When("I click View on the search results", () => {
  classicOrganizationPage.clickViewOnSearchResults();
});

// Address Information steps
When("I select address use {string}", (useDisplay) => {
  editOrganizationPage.enterAddressUse(maps.addressUseToValue(useDisplay));
});

When("I select address type {string}", (typeDisplay) => {
  editOrganizationPage.enterAddressType(maps.addressTypeToValue(typeDisplay));
});

When("I enter street address {string}", (address) => {
  editOrganizationPage.enterStreetAddress1(address);
});

When("I enter city {string}", (city) => {
  editOrganizationPage.enterCity(city);
});

When("I enter zip code {string}", (zip) => {
  editOrganizationPage.enterZipCode(zip);
});

When("I click the Add Address button", () => {
  editOrganizationPage.clickAddAddressButton();
});


// Telephone Information steps
When("I select telephone use {string}", (useDisplay) => {
  editOrganizationPage.enterTelephoneUse(maps.telephoneUseToValue(useDisplay));
});

When("I select telephone type {string}", (typeDisplay) => {
  editOrganizationPage.enterTelephoneType(maps.telephoneTypeToValue(typeDisplay));
});

When("I enter telephone number {string}, {string}, {string}", (areaCode, prefix, lineNumber) => {
  editOrganizationPage.enterTelephoneNumber(areaCode, prefix, lineNumber);
});

When("I click the Add Telephone button", () => {
  editOrganizationPage.clickAddTelephoneButton();
});

When("I add a new telephone with use {string}, type {string}, number {string}-{string}-{string}", 
  (useDisplay, typeDisplay, areaCode, prefix, lineNumber) => {
    editOrganizationPage.addNewTelephoneEntry({
      use: maps.telephoneUseToValue(useDisplay),
      type: maps.telephoneTypeToValue(typeDisplay),
      areaCode: areaCode,
      prefix: prefix,
      lineNumber: lineNumber
    });
});

// ID Information steps
When("I add a new ID record with type {string}, authority {string}, value {string}", 
  (typeDisplay, authorityDisplay, value) => {
    editOrganizationPage.addNewIdEntry({
      type: maps.idTypeToValue(typeDisplay),
      authority: maps.idAuthorityToValue(authorityDisplay),
      idValue: value
    });
});


// Edit organization steps
When("I click the Edit button", () => {
  classicOrganizationPage.clickEditButton();
});

When("I select {string} as the reason for edit", (reasonDisplay) => {
  editOrganizationPage.selectEditReason(maps.editReasonToValue(reasonDisplay));
});

When("I enter a unique quick code", () => {
  editOrganizationPage.enterUniqueQuickCode();
});

When("I delete the existing address", () => {
  editOrganizationPage.deleteExistingAddress();
});

// Inactivate organization steps
When("I click the Inactivate button", () => {
  classicOrganizationPage.clickInactivateButton();
});

When("I confirm the inactivation", () => {
  classicOrganizationPage.acceptConfirmationPopup();
});

// Verification steps
Then("the search results should include {string}", (expectedText) => {
  classicOrganizationPage.verifySearchResultsContain(expectedText)
});

Then("there should be {int} more search result than before", (additionalResultCount) => {
  classicOrganizationPage.verifySearchResultsCountIncreasedBy(additionalResultCount);
});

Then("one of the search results should have the generated quick code", () => {
  classicOrganizationPage.verifySearchResultContainsGeneratedQuickCode();
});

Then("the search results should include address {string}", (expectedAddress) => {
  classicOrganizationPage.verifySearchResultsContainAddress(expectedAddress)
});

Then("I should see a message that no matching organizations were found", () => {
  classicOrganizationPage.verifyNoResultsFound()
});

Then("the organization should appear in search results", () => {
  classicOrganizationPage.verifyOrganizationWasCreated();
});

Then("I should see a success message", () => {
  classicOrganizationPage.verifySuccessMessage()
});

Then("I should see all organization details are correct", () => {
  classicOrganizationPage.verifyAllOrganizationDetails();
});

Then("the search results should include telephone {string}", (expectedTelephone) => {
  classicOrganizationPage.verifySearchResultsContainTelephone(expectedTelephone);
});

Then("the search results should include ID Type {string} with ID Value {string}", (expectedType, expectedValue) => {
  classicOrganizationPage.verifySearchResultsContainIdTypeAndValue(expectedType, expectedValue);
});