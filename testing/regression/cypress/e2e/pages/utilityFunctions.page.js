import { faker } from "@faker-js/faker";

class UtilityFunctions {
  getRandomLetter() {
    const letters = 'abcdefghijklmnopqrstuvwxyz';
    const randomIndex = Math.floor(Math.random() * letters.length);
    return letters[randomIndex];
  }

  generateRandomSSN() {
    return Array(9).fill().map(() => faker.number.int(9)).join('');
  }

  generateTimestamp() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    return `${year}${month}${day}${hours}${minutes}`;
  }

  capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }

  generateRandomLastName() {
    let randomLastName = faker.person.lastName().toLowerCase() + this.getRandomLetter() + this.getRandomLetter() + this.getRandomLetter() + this.getRandomLetter() + this.getRandomLetter();
    randomLastName = this.capitalizeFirstLetter(randomLastName).replace(/[^0-9a-z]/gi, '');
    return randomLastName;
  }

  formatSSN(ssn) {
    const ssnString = ssn.toString();
    return `${ssnString.slice(0, 3)}-${ssnString.slice(3, 5)}-${ssnString.slice(5)}`;
  }

  replacePlaceholders(message, replacements) {
    return Object.entries(replacements).reduce((modifiedMessage, [placeholder, replacement]) => {
      return modifiedMessage.replaceAll(placeholder, replacement);
    }, message);
  }

  formatHL7 = (hl7String) => {
    const formattedFields = [];
    const segments = hl7String.split(/\r\n|\r|\n/);

    segments.forEach(segment => {
      const fields = segment.split('|');
      for (let i = 1; i < fields.length; i++) {
        const components = fields[i].split('^');
        if (components.length > 1) {
          components.forEach((component, j) => {
            formattedFields.push(`${fields[0]}.${i}.${j + 1} - ${component}`);
          });
        } else {
          formattedFields.push(`${fields[0]}.${i} - ${fields[i]}`);
        }
      }
    });

    return formattedFields;
  };

  checkELRActivityLog(fakeRandomData) {
    cy.get("a").contains("Home").click();              
    cy.contains('System Management').click();
    cy.xpath("/html/body/div/div/div[2]/div/table[5]/thead/tr/th/a/img").click();              
    cy.contains('Manage ELR Activity Log').click();
    cy.get("#searchButton").click()
    cy.xpath("/html/body/div[2]/div[1]/form/div[4]/div/fieldset/table/tbody/tr/td/table/thead/tr/th[7]/img").click();
    cy.get("#SearchText4").type(fakeRandomData.randomLastName);
    cy.get("#b2SearchText4").first().click();
    cy.contains(fakeRandomData.randomLastName).should("be.visible");
    cy.contains("Successfully Create Notification").should("be.visible");
  }

  createNotication(string) {
    cy.get("input[name=createInvestigation]").first().click();
    cy.get("select[name=ccd]").select(string, { force: true });
    cy.get("input[name=Submit]").first().click();
    cy.get('#DEM196').invoke('text', 'Investigation is needed');
    cy.get("select[id=INV163]").select("Confirmed", { force: true });
    cy.get("input[name=SubmitTop]").first().click();
    cy.window().then(win => {
      win.createNotifications('Comment');
      return cy.get("#successMessages").contains("A Notification has been created for this Investigation.").scrollIntoView().should("be.visible");
    });
  }
}

export default new UtilityFunctions();
