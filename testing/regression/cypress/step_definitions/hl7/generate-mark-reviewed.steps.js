import {
  When,
  Then,
  attach,
  Given,
} from "@badeball/cypress-cucumber-preprocessor";
import "cypress-xpath";
import UtilityFunctions from "@pages/utilityFunctions.page";
import { faker } from "@faker-js/faker";

When("I Generate HL7 messages to api and mark as review", (string) => {
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

    cy.log(
      `${randomData.randomLastName}, ${randomData.randomFirstName} ${formattedSSN}`
    );
    currentMessage = UtilityFunctions.formatHL7(modifiedData);
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
          function markAsReviewed() {
            cy.get("a").contains("Home").click();
            cy.contains("Documents Requiring Review").click();
            cy.xpath(
              "/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/thead/tr/th[5]/img"
            ).click();
            cy.get("#SearchText1").type(fakeRandomData.randomLastName);
            cy.get("#b2SearchText1").first().click();
            cy.xpath(
              "/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/tbody/tr/td[2]/a"
            ).click();
            cy.get("input[name=markReviewd]").first().click();
            cy.get("input[name=Submit]").first().click();
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
            markAsReviewed();
          }
        });
      };
      checkStatusRequest();
    });
  });
});
