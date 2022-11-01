import { defineConfig } from 'cypress';

export default defineConfig({
    env: {
        detailedLogs: false
    },

    e2e: {
        setupNodeEvents(on, config) {
            return require('./cypress/plugins/index.js')(on, config);
        },
        specPattern: 'cypress/e2e/**/*.feature',
        scrollBehavior: 'center',
        baseUrl: 'http://localhost:3000/',
        retries: { runMode: 2, openMode: 0 },
        supportFile: 'cypress/support/e2e.{js,jsx,ts,tsx}'
    },

    component: {
        devServer: {
            framework: 'react',
            bundler: 'webpack'
        },
        supportFile: 'cypress/support/e2e.{js,jsx,ts,tsx}'
    }
});
