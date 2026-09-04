import createBundler from '@bahmutov/cypress-esbuild-preprocessor';
import preprocessor from '@badeball/cypress-cucumber-preprocessor';
import { createEsbuildPlugin } from '@badeball/cypress-cucumber-preprocessor/esbuild';
import fs from 'fs/promises';
import * as db from '@dankieu/cypress-sql';
import { defineConfig } from 'cypress';

function isFailedResult(
    result: CypressCommandLine.CypressRunResult | CypressCommandLine.CypressFailedRunResult
): result is CypressCommandLine.CypressFailedRunResult {
    return (result as CypressCommandLine.CypressFailedRunResult).failures > 0;
}

async function setupNodeEvents(on: Cypress.PluginEvents, config: Cypress.PluginConfigOptions) {
    await preprocessor.addCucumberPreprocessorPlugin(on, config);

    on(
        'file:preprocessor',
        createBundler({
            plugins: [createEsbuildPlugin(config)],
        })
    );
    on('after:run', async (result: CypressCommandLine.CypressRunResult | CypressCommandLine.CypressFailedRunResult) => {
        if (result) {
            if (isFailedResult(result)) {
                console.error(`Cypress run failed with ${result.failures} failures.`);
            } else {
                await preprocessor.afterRunHandler(config);
                await fs.writeFile(
                    'results.json',
                    JSON.stringify(
                        {
                            browserName: result.browserName,
                            browserVersion: result.browserVersion,
                            osName: result.osName,
                            osVersion: result.osVersion,
                            nodeVersion: result.config.resolvedNodeVersion,
                            cypressVersion: result.cypressVersion,
                            startedTestsAt: result.startedTestsAt,
                            endedTestsAt: result.endedTestsAt,
                        },
                        null,
                        '\t'
                    )
                );
            }
        }
    });
    db.sqlServer(on); // adds cy.task("sqlServer") to query DB
    return config;
}

export default defineConfig({
    e2e: {
        setupNodeEvents,
        specPattern: './cypress/**/**/*.feature',
        baseUrl: 'http://localhost:8000/',
        chromeWebSecurity: false,
        video: false,
        watchForFileChanges: false,
        pageLoadTimeout: 180000,
        defaultCommandTimeout: 20000,
        requestTimeout: 60000,
        responseTimeout: 60000,
    },
    retries: {
        // Configure retry attempts for `cypress run`
        runMode: 1,
        // Configure retry attempts for `cypress open`
        openMode: 0,
    },
});
