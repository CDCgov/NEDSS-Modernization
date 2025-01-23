import {
  When,
  Then,
  attach,
  Given,
} from "@badeball/cypress-cucumber-preprocessor";
import "cypress-xpath";
import UtilityFunctions from "@pages/utilityFunctions.page";
import { faker } from "@faker-js/faker";

When(
  "I Generate HL7 message, create investigation {string}, and submit a notification",
  (string) => {
    let messageCondition = string;
    let fakeFullName;
    let currentMessage;
    let messageID;
    let NBSresponse;
    let fakeFormattedSSN;
    let fakeRandomData;
    const authToken = Cypress.env("authTokenAPI");
    const clientid = Cypress.env("DI_CLIENT_ID");
    const clientsecret = Cypress.env("DI_SECRET");
    const apiurl = Cypress.env("apiurl");
    const checkstatusurl = Cypress.env("checkstatusurl");
    const authurl = Cypress.env("authurl");

    cy.readFile("cypress/fixtures/try.json", "utf8").then((jsonData) => {
      const randomData = {
        randomFirstName: faker.person.firstName(),
        randomLastName: UtilityFunctions.generateRandomLastName(),
        fakeSSN: UtilityFunctions.generateRandomSSN(),
        fakeEmail: faker.internet.email(),
        fakeStreetAddress: faker.address.streetAddress(),
        fakeState: faker.address.stateAbbr(),
        fakeCity: faker.address.city(),
        fakeBuildingNumber: faker.address.buildingNumber(),
        fakeDOB: `19${faker.number.int(9)}${faker.number.int(9)}`,
        faketimestamp: UtilityFunctions.generateTimestamp(),
      };

      const replacements = {
        PawnlandFirstName: randomData.randomFirstName,
        PawnlandLastName: randomData.randomLastName,
        "patient.email@example.com": randomData.fakeEmail,
        900000011: randomData.fakeSSN,
        1965: randomData.fakeDOB,
        Joneshaven: randomData.fakeCity,
        "9315 Gonzalez Mountains": randomData.fakeStreetAddress,
        "unit 19028": "unit " + randomData.fakeBuildingNumber,
        NY: randomData.fakeState,
        202407111207: randomData.faketimestamp,
      };

      let modifiedmsg = jsonData[0].data;
      let modifiedData = UtilityFunctions.replacePlaceholders(
        modifiedmsg,
        replacements
      );
      const formattedSSN = UtilityFunctions.formatSSN(randomData.fakeSSN);
      currentMessage = UtilityFunctions.formatHL7(modifiedData);

      cy.log(
        `${randomData.randomLastName}, ${randomData.randomFirstName} ${formattedSSN}`
      );

      fakeFullName = `${randomData.randomLastName}, ${randomData.randomFirstName}`;
      fakeRandomData = randomData;
      fakeFormattedSSN = formattedSSN;

      Cypress.env("currentMessage", currentMessage);
      Cypress.env(
        "fakeFullName",
        `${randomData.randomLastName}, ${randomData.randomFirstName}`
      );
      Cypress.env("randomData", randomData);
      cy.request({
        method: "POST",
        url: apiurl,
        headers: {
          "Content-Type": "text/plain",
          Authorization: `Bearer ${authToken}`,
          clientid: clientid,
          clientsecret: clientsecret,
          msgType: "HL7",
        },
        body: modifiedData,
      }).then((response) => {
        messageID = response.body;
        const checkStatusUrl = `${checkstatusurl}${messageID}`;

        const checkStatusRequest = () => {
          cy.request({
            method: "GET",
            url: checkStatusUrl,
            headers: {
              Authorization: `Bearer ${authToken}`,
              clientid: clientid,
              clientsecret: clientsecret,
            },
          }).then((response) => {
            function navigateAndSearchDocuments() {
              // Navigate to home
              cy.get("a").contains("Home").click();
              // Navigate to 'Documents Requiring Review'
              cy.contains("Documents Requiring Review").click();
              // Click the table header to sort
              cy.xpath(
                "/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/thead/tr/th[5]/img"
              ).click();
              // Search for the random last name
              cy.get("#SearchText1").type(fakeRandomData.randomLastName);
              cy.get("#b2SearchText1").first().click();
              // Click the first result
              cy.xpath(
                "/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/tbody/tr/td[2]/a"
              ).click();
              // Mark as reviewed and transfer ownership
              cy.get("input[name=markReviewd]").first().click();
              cy.get("input[name=TransferOwn]").first().click();
              cy.get("input[name=Submit]").first().click();
              // Return to 'Documents Requiring Review'
              cy.contains("Return to Documents Requiring Review").click();
              // Navigate back to home
              cy.contains("Home").click();
              // Advanced search
              cy.get("#homePageAdvancedSearch").click();
              cy.get("input[id=lastName]").type(fakeRandomData.randomLastName);
              cy.get("input[id=firstName]").type(fakeRandomData.randomFirstName);
              cy.get("h3").contains("ID").click();
              cy.get("#identificationType").select("Social Security");
              cy.get("input[name='identification']").type(fakeFormattedSSN);
              cy.get("button").contains("Search").click();
              cy.get("button").contains("List").click();
            }

            function ClickAdvancedSearch() {
              cy.contains("Home").click();

              // Advanced search
              cy.get("#homePageAdvancedSearch").click();
              cy.get("#lastName").type(fakeRandomData.randomLastName);
              cy.get("#firstName").type(fakeRandomData.randomFirstName);
              cy.get("h3").contains("ID").click();
              cy.get("#identificationType").select("Social Security");
              cy.get("input[name='identification']").type(fakeFormattedSSN);
              cy.get("button").contains("Search").click();
              cy.get("button").contains("List").click();
              cy.get("body").then(($body) => {
                if ($body.find("#legalName").length > 0) {
                  cy.get("a")
                    .contains(fakeFullName)
                    .then(($button) => {
                      if ($button.is(":visible")) {
                        cy.contains(fakeFormattedSSN)
                          .scrollIntoView()
                          .should("be.visible");
                        cy.get("a")
                          .contains(fakeFullName)
                          .scrollIntoView()
                          .click({ force: true });
                        cy.get("a").contains("Events").click({ force: true });
                        cy.get("#classic a").first().click();
                        UtilityFunctions.createNotication(messageCondition);
                        cy.get("#successMessages")
                          .contains(
                            "A Notification has been created for this Investigation."
                          )
                          .scrollIntoView()
                          .should("be.visible");
                      }
                    });
                } else {
                  cy.wait(20000).then(ClickAdvancedSearch);
                }
              });
            }

            cy.wait(2000);
            expect(response.status).to.eq(200);

            if (
              response.body[0].nbsInfo.nbsInterfaceStatus === "QUEUED" ||
              response.body[0].nbsInfo.nbsInterfacePipeLineStatus === "IN PROGRESS"
            ) {
              cy.wait(20000).then(checkStatusRequest);
            } else if (
              response.body[0].nbsInfo.nbsInterfaceStatus === "Success" &&
              response.body[0].nbsInfo.nbsInterfacePipeLineStatus === "COMPLETED"
            ) {
              navigateAndSearchDocuments();
              cy.wait(2000);
              cy.get("body").then(($body) => {
                if ($body.find("#legalName").length > 0) {
                  cy.get("a")
                    .contains(fakeFullName)
                    .then(($button) => {
                      if ($button.is(":visible")) {
                        cy.contains(fakeFormattedSSN)
                          .scrollIntoView()
                          .should("be.visible");
                        cy.get("a")
                          .contains(fakeFullName)
                          .scrollIntoView()
                          .click({ force: true });
                        cy.get("a").contains("Events").click({ force: true });
                        cy.get("#classic a").first().click();
                        UtilityFunctions.createNotication(messageCondition);
                      }
                    });
                } else {
                  cy.wait(20000).then(ClickAdvancedSearch);
                }
              });
            }
          });
        };
        checkStatusRequest();
      });
    });
  }
);
