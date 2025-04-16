## Cypress Testing – Overview

**[Cypress](https://www.cypress.io/)** is a modern, open-source JavaScript-based end-to-end testing framework built for web applications. It is known for its **fast**, **reliable**, and **developer-friendly** features.

### Key Features

- **Runs in the browser**: Cypress executes tests in the same run loop as your app, giving it native access to the DOM, network requests, etc.
- **Automatic waiting**: No need to add manual waits or sleeps; Cypress waits for commands and assertions to pass.
- **Real-time reloads**: As you save your test files, Cypress automatically reloads the test runner.
- **Time travel debugging**: Cypress captures snapshots of your test steps, so you can go back in time and see exactly what happened.


### Prerequisites

### 1. Clone or Download project
[NEDSS Modernization Git Repository](https://github.com/CDCgov/NEDSS-Modernization)

### 2. Install Node
[Node Page](https://nodejs.org/en)

### Minimum Requirements
- **npm**: `10.5.0`
- **Node.js**: `v21.7.2`

> **Warning:** The data created by Cypress tests is not cleaned up. Running automated testing against your database **WILL** pollute it with random data.


> **Warning:** SSO sign in is not supported. Attempting to connect to an environment with SSO enabled will fail.



## Running Cypress (Linux / Mac)
### 1. Navigate to the Project Directory

```bash
cd testing/regression
```

### 2. Install Project Dependencies

```bash
npm install
```

### 3. Install Cypress

```bash
./node_modules/cypress/bin/cypress install
# or
npm install cypress --save-dev
```

### 4. Create and Configure `cypress.env.json`
Create a new `cypress.env.json` file in the `testing/regression` directory.

The following content will configure Cypress to connect to the INT1 environment.

```json
{
  "DI_API": "https://dataingestion.int1.nbspreview.com/ingestion/api",
  "NOTIFICATION_STATUS_API": "https://app.int1.nbspreview.com/nbs/api/investigations/uid/notifications/transport/status",
  "ON_PRIM_NOTIFICATION_STATUS_API": "https://testsync.dts1.nbspreview.com/notifications/uid/status",
  "DI_CLIENT_ID": "<di-keycloak-client>",
  "DI_SECRET": "<data-ingestion-secret",
  "LOGIN_USERNAME": "<username>",
  "LOGIN_PASSWORD": "<password>"
}
```

|Environment variable|Use|
|-----|-----|
|DI_API| Base route for Data Ingestion API|
|NOTIFICATION_STATUS_API|API that returns the processing status of HL7 messages created by `DI_API`|
|ON_PRIM_NOTIFICATION_STATUS_API|API that returns the final data has been processed into a simulated STLT environment|
|DI_CLIENT_ID| Data ingestion keycloak client|
|DI_SECRET | Data ingestion keycloak secret|
|LOGIN_USERNAME| Username for UI authentication|
|LOGIN_PASSWORD| Password for UI authentication|

> **Note**: `cypress.env.json` is ignored by git via `.gitignore` and should never be committed.

### 5. Set `baseUrl` in `cypress.config.js`
The default values for `baseUrl` within `cypress.config.js` point to a local environment. To connect to the INT1 environment, set the `baseUrl` value as follows:
```js
module.exports = defineConfig({
  e2e: {
    setupNodeEvents,
    specPattern: "./cypress/**/**/*.feature",
    baseUrl: "https://app.int1.nbspreview.com/",
    chromeWebSecurity: false,
    video: false,
  },
});
```

### 7. Open Cypress

```bash
npx cypress open
```

> **Note**: An error will show if `baseUrl` has not been changed or the local dev server is not running.
