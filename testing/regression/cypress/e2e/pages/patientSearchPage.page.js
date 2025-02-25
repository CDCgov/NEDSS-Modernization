class PatientSearchPage {
    /**
     * Selects "Exact Date" for Date of Birth search.
     */
    selectExactDate() {
      cy.get('label[for="equals"]').click();
    }
  
    /**
     * Enters a valid specific date of birth in the Exact Date fields.
     */
    enterExactDateOfBirth() {
      const today = new Date();
      const validYear = today.getFullYear() - Math.floor(Math.random() * 80) - 20; // Ensures a realistic DOB (between 1900 - current year)
      const validMonth = String(Math.floor(Math.random() * 12) + 1).padStart(2, "0");
      const validDay = String(Math.floor(Math.random() * 28) + 1).padStart(2, "0"); // Limits day to 28 for safety
  
      cy.log(`Entering Valid DOB: ${validMonth}/${validDay}/${validYear}`);
  
      cy.get("#bornOn-exact-date-month").should("be.visible").clear().type(validMonth);
      cy.get("#bornOn-exact-date-day").should("be.visible").clear().type(validDay);
      cy.get("#bornOn-exact-date-year").should("be.visible").clear().type(validYear);
    }
  
    /**
     * Clicks the Search button.
     */
    clickSearch() {
        cy.get('button[data-testid="button"]').contains("Search").click();
      }
  
    /**
     * Verifies that results are displayed.
     */
    verifySearchResults() {
      cy.contains("Perform a search to see results").should("not.exist");
    }

  selectDateRange() {
    cy.get('label[for="between"]').click();
  }

  enterDateRange() {
    const today = new Date();
    const startYear = today.getFullYear() - 30; // Randomly select a year 30 years ago
    const endYear = today.getFullYear() - 10;  // Randomly select a year 10 years ago

    const startDate = `01/01/${startYear}`;
    const endDate = `12/31/${endYear}`;

    cy.log(`Entering Date Range: From ${startDate} To ${endDate}`);

    cy.get("#bornOn-range-entry-from").should("be.visible").clear().type(startDate);
    cy.get("#bornOn-range-entry-to").should("be.visible").clear().type(endDate);
  }

  verifySearchResults() {
    cy.contains("Perform a search to see results").should("not.exist");
  }

  enterInvalidDateOfBirth() {
    const futureYear = new Date().getFullYear() + 1; // Next year (invalid)
    const invalidMonth = "13"; // Invalid month
    const invalidDay = "32"; // Invalid day

    cy.log(`Entering Invalid DOB: ${invalidMonth}/${invalidDay}/${futureYear}`);

    cy.get("#bornOn-exact-date-month").should("be.visible").clear().type(invalidMonth);
    cy.get("#bornOn-exact-date-day").should("be.visible").clear().type(invalidDay);
    cy.get("#bornOn-exact-date-year").should("be.visible").clear().type(futureYear);
  }

  verifyErrorMessage() {
    cy.get("#bornOn-error")
      .should("be.visible")
      .invoke("text")
      .then((text) => {
        cy.log(`Error Message Displayed: ${text}`);
        expect(text.trim()).to.not.be.empty; // Ensures an error message exists
      });
    }
}
  
  export default new PatientSearchPage();
  