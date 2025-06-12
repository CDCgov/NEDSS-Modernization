const eslintPluginCypress = require('eslint-plugin-cypress')
const js = require('@eslint/js')

module.exports = [
  js.configs.recommended,
  {
    files: ['cypress/**/*.js', 'cypress/**/*.ts'],
    plugins: { cypress: eslintPluginCypress },
    languageOptions: {
      globals: {
        ...eslintPluginCypress.environments.globals.globals
      }
    },
    rules: {
      'cypress/no-unnecessary-waiting': 'error' // Example Cypress rule
    }
  }
]