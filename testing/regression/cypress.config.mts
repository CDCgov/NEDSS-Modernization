import createBundler from '@bahmutov/cypress-esbuild-preprocessor';
import preprocessor from '@badeball/cypress-cucumber-preprocessor';
import * as createEsbuildPlugin from '@badeball/cypress-cucumber-preprocessor/esbuild';
import fs from 'fs/promises';
import * as db from '@dankieu/cypress-sql';
import { defineConfig } from 'cypress';

async function setupNodeEvents(on: any, config: any) {
    await preprocessor.addCucumberPreprocessorPlugin(on, config);

    on(
        'file:preprocessor',
        createBundler({
            plugins: [createEsbuildPlugin.default(config)],
        })
    );
    on('after:run', async (results: any) => {
        if (results) {
            await preprocessor.afterRunHandler(config);
            await fs.writeFile(
                'results.json',
                JSON.stringify(
                    {
                        browserName: results.browserName,
                        browserVersion: results.browserVersion,
                        osName: results.osName,
                        osVersion: results.osVersion,
                        nodeVersion: results.config.resolvedNodeVersion,
                        cypressVersion: results.cypressVersion,
                        startedTestsAt: results.startedTestsAt,
                        endedTestsAt: results.endedTestsAt,
                    },
                    null,
                    '\t'
                )
            );
        }
    });
    db.sqlServer(on); // adds cy.task("sqlServer") to query DB
    return config;
}

module.exports = defineConfig({
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
