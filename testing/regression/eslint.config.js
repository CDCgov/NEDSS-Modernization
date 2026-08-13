const eslintPluginCypress = require('eslint-plugin-cypress')
const js = require('@eslint/js')

// adapted from apps/modernization-ui/eslint.config.mjs
const baseRules = {
  ...js.configs.recommended.rules,
  'no-console': 'warn',
  'max-len': ['warn', { code: 120 }],
  'dot-notation': 'error',
  eqeqeq: 'error',
  'no-var': 'error',
  'prefer-const': 'error',
  'object-shorthand': 'error',
};


const cypressRules = {
  'cypress/no-unnecessary-waiting': 'warn' // change to error once resolved
};

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
      ...baseRules,
      ...cypressRules,
      'max-len': 'off',
    }
  }
]