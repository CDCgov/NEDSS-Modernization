// / <reference types="cypress" />

// This overrides the default logging cypress does to hide the XHR requests that spam the window
// Cypress issue: https://github.com/cypress-io/cypress/issues/7362
const origLog = Cypress.log;
const detailedLogs = Cypress.env('detailedLogs');
Cypress.log = function (opts, ...other) {
    if (
        !detailedLogs &&
        (opts.displayName === 'script' ||
            opts.name === 'request' ||
            opts.name?.indexOf('uncaught exception') > -1 ||
            opts.name?.indexOf('xhr') > -1)
    ) {
        return;
    }
    return origLog(opts, ...other);
};
