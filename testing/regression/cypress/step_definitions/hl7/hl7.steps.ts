import { Then, When } from "@badeball/cypress-cucumber-preprocessor";
import { faker } from "@faker-js/faker";

const { Given } = require("@badeball/cypress-cucumber-preprocessor");

let hl7Message: string | undefined;
let patientData: Patient | undefined;
let messageId: string | undefined;
let investigationId: string | undefined;
let notificationId: string | undefined;

Given("I am authenticated with the DI API", () => {
  const clientid = Cypress.env("DI_CLIENT_ID");
  const secret = Cypress.env("DI_SECRET");
  const baseUrl = Cypress.env("DI_API");

  cy.log("Fetching api token...");
  cy.request({
    method: "POST",
    url: `${baseUrl}/auth/token`,
    headers: {
      "Content-Type": "text/plain",
      clientid: clientid,
      clientsecret: secret,
    },
  }).then((response) => {
    expect(response.status, "Token API return status").to.eq(200);
    Cypress.env("di_token", response.body);
  });
});

Given("I have a HL7 message containing a {string} test", (testType: string) => {
  const fixtureName: string = (() => {
    switch (testType) {
      case "Hepatitis B":
        return "hepb";
      case "Hepatitis A":
        return "hepa";
      default:
        return testType;
    }
  })();

  patientData = {
    firstName: faker.person.firstName(),
    lastName: generateRandomLastName(),
    ssn: generateRandomNumbers(9),
    email: faker.internet.email(),
    street: faker.location.streetAddress(),
    state: faker.location.state({ abbreviated: true }),
    city: faker.location.city(),
    buildingNumber: `unit ${faker.location.buildingNumber()}`,
    dob: `19${faker.number.int(9)}${faker.number.int(9)}`,
    timestamp: generateTimestamp(),
  };

  cy.log("loading base hl7...");
  cy.readFile(`cypress/fixtures/${fixtureName}.json`, "utf8").then(
    (file: { data: string }[]) => {
      cy.log("updating hl7 patient data...");
      hl7Message = file[0].data
        .replace(/PawnlandFirstName/g, patientData.firstName)
        .replace(/PawnlandLastName/g, patientData.lastName)
        .replace(/SSNHOLDER/g, patientData.ssn)
        .replace(/BIRTHYEAR/g, patientData.dob)
        .replace(/PATIENTCITY/g, patientData.city)
        .replace(/STREETADDRESS/g, patientData.street)
        .replace(/PATIENTUNITADDRESS/g, patientData.buildingNumber)
        .replace(/PATIENTSTATEADDRESS/g, patientData.state)
        .replace(/UUIDTIMESTAMP/g, patientData.timestamp);

      console.log("updated hl7:", hl7Message);
    }
  );
});

When("I submit the HL7 message", () => {
  expect(hl7Message).not.to.be.null;
  const apiurl = Cypress.env("apiurl");
  const authToken = Cypress.env("di_token");
  const clientid = Cypress.env("DI_CLIENT_ID");
  const clientsecret = Cypress.env("DI_SECRET");

  cy.log("Submitting HL7 message to DI API...");

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
    body: hl7Message,
  }).then((response: { body: string }) => {
    expect(response.body).not.to.be.null;
    messageId = response.body;
  });
});

Then("the HL7 message is processed by the data ingestion service", () => {
  const baseUrl = Cypress.env("DI_API");
  const clientid = Cypress.env("DI_CLIENT_ID");
  const secret = Cypress.env("DI_SECRET");
  const authToken = Cypress.env("di_token");

  let status = "";
  const maxAttempts = 30;
  const delay = 10_000;

  const checkStatus = (attempts = 0) => {
    if (attempts >= maxAttempts) {
      return;
    }

    cy.request({
      method: "GET",
      url: `${baseUrl}/elrs/status/${messageId}`,
      headers: {
        Authorization: `Bearer ${authToken}`,
        clientid: clientid,
        clientsecret: secret,
      },
    }).then((response: { body: string }) => {
      status = JSON.parse(response.body).status;
      cy.log("Recieved status of: " + status);
      if (status === "Success") {
        return;
      } else {
        cy.wait(delay).then(() => checkStatus(++attempts));
      }
    });
  };
  checkStatus();
  cy.then(() => expect(status).to.equal("Success"));
});

Then("an Investigation is created for the HL7 message", () => {
  // Navigate to ELR activity log
  cy.get("a").contains("Home").click();
  cy.contains("System Management").click();
  cy.xpath("/html/body/div/div/div[2]/div/table[5]/thead/tr/th/a/img").click();
  cy.contains("Manage ELR Activity Log").click();

  // Search for recent records
  cy.get("#searchButton").click();

  // Search by patient name
  cy.xpath(
    "/html/body/div[2]/div[1]/form/div[4]/div/fieldset/table/tbody/tr/td/table/thead/tr/th[7]/img"
  ).click();
  cy.get("#SearchText4").type(patientData.lastName);
  cy.get("#b2SearchText4").first().click();

  // Verify patient is found
  cy.contains(patientData.lastName).should("be.visible");
  cy.contains("Successfully Create Notification").should("be.visible");

  // Select details
  cy.get(".dtTable td a").eq(0).click();

  // Extract Investigation UID from table
  cy.xpath('//*[@id="parent"]/tbody/tr[7]/td[2]')
    .invoke("text")
    .then((content) => {
      investigationId = content.match(/\(UID:\s*(\d+)\)/)[1];
      expect(investigationId, "Investigation UID").is.not.null;
    });
});

Then(
  "the Investigation has a notification with a status of {string}",
  (exectedStatus: string) => {
    expect(investigationId, "Investigation Id").is.not.null;

    const transportstatusurl = Cypress.env("NOTIFICATION_STATUS_API").replace(
      "uid",
      investigationId
    );

    let status = "";
    const maxAttempts = 30;
    const delay = 10_000;

    const checkStatus = (attempts = 0) => {
      if (attempts >= maxAttempts) {
        return;
      }

      cy.request({
        method: "GET",
        url: `${transportstatusurl}`,
      }).then((response: { body: { status: string; localId: string } }) => {
        status = response.body.status;
        cy.log("Recieved status of: " + response.body.status);
        if (status === exectedStatus) {
          notificationId = response.body.localId;
          expect(notificationId, "Notification Id").not.to.be.null;
        } else {
          cy.wait(delay).then(() => checkStatus(++attempts));
        }
      });
    };

    checkStatus();
    cy.then(() => expect(status).to.equal(exectedStatus));
  }
);
Then(
  "the Notification is copied onto the on-prem database with a status of {string}",
  (exectedStatus: string) => {
    expect(notificationId, "Investigation Id").is.not.null;

    const transportstatusurl = Cypress.env(
      "ON_PRIM_NOTIFICATION_STATUS_API"
    ).replace("uid", notificationId);

    let status = "";
    const maxAttempts = 30;
    const delay = 10_000;

    const checkStatus = (attempts = 0) => {
      if (attempts >= maxAttempts) {
        return;
      }

      cy.request({
        method: "GET",
        url: `${transportstatusurl}`,
      }).then(
        (response: {
          body: { transportStatus: string; netssTransportStatus: string };
        }) => {
          status = response.body.transportStatus
            ? response.body.transportStatus
            : response.body.netssTransportStatus;
          cy.log("Recieved status of: " + status);
          if (status === exectedStatus) {
            return;
          } else {
            cy.wait(delay).then(() => checkStatus(++attempts));
          }
        }
      );
    };

    checkStatus();
    cy.then(() => expect(status).to.equal(exectedStatus));
  }
);

const getRandomLetters = (count: number): string => {
  let chars = "";
  for (let i = 0; i < count; i++) {
    // 97 == a, 122 == z
    chars += String.fromCharCode(97 + Math.random() * 25);
  }
  return chars;
};

const generateRandomLastName = () => {
  let randomLastName =
    faker.person.lastName().toLowerCase() + getRandomLetters(5);
  return (
    randomLastName.charAt(0).toUpperCase() + randomLastName.slice(1)
  ).replace(/[^0-9a-z]/gi, "");
};

const generateRandomNumbers = (count: number): string => {
  return Array(count)
    .fill([])
    .map(() => faker.number.int(9))
    .join("");
};

// creates a timestamp in YYYYMMddHHmm format
const generateTimestamp = (): string => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");
  const hours = String(now.getHours()).padStart(2, "0");
  const minutes = String(now.getMinutes()).padStart(2, "0");
  return `${year}${month}${day}${hours}${minutes}`;
};

type Patient = {
  firstName: string;
  lastName: string;
  ssn: string;
  email: string;
  street: string;
  state: string;
  city: string;
  buildingNumber: string;
  dob: string;
  timestamp: string;
};
