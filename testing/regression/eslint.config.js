import eslintPluginCypress from 'eslint-plugin-cypress';
import js from '@eslint/js';
import tsParser from '@typescript-eslint/parser';
import { defineConfig } from 'eslint/config';

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
    ...eslintPluginCypress.configs.recommended.rules,
    'cypress/no-unnecessary-waiting': 'warn', // change to error once resolved
};

export default defineConfig([
    js.configs.recommended,
    {
        files: ['cypress/**/*.js', 'cypress/**/*.ts'],
        plugins: { cypress: eslintPluginCypress },
        languageOptions: {
            parser: tsParser,
            globals: {
                ...eslintPluginCypress.environments.globals.globals,
            },
        },
        rules: {
            ...baseRules,
            ...cypressRules,
            'max-len': 'off',
        },
        settings: {
            'import/parsers': {
                '@typescript-eslint/parser': ['.ts', '.tsx'],
            },
        },
    },
]);
