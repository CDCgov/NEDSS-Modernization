import { fixupConfigRules, fixupPluginRules } from '@eslint/compat';
import react from 'eslint-plugin-react';
import typescriptEslint from '@typescript-eslint/eslint-plugin';
import globals from 'globals';
import tsParser from '@typescript-eslint/parser';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import js from '@eslint/js';
import { FlatCompat } from '@eslint/eslintrc';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const compat = new FlatCompat({
    baseDirectory: __dirname,
    recommendedConfig: js.configs.recommended,
    allConfig: js.configs.all
});

export default [
    {
        ignores: ['src/generated/**/*']
    },
    ...fixupConfigRules(
        compat.extends(
            // 'google',
            // 'eslint:recommended',
            'plugin:react/recommended',
            'plugin:prettier/recommended',
            'plugin:react-hooks/recommended'
        )
    ),
    {
        plugins: {
            react: fixupPluginRules(react),
            '@typescript-eslint': typescriptEslint
        },

        languageOptions: {
            globals: {
                ...globals.browser,
                ...globals.jest,
                JSX: 'readonly'
            },

            parser: tsParser,
            ecmaVersion: 'latest',
            sourceType: 'script',

            parserOptions: {
                ecmaFeatures: {
                    jsx: true
                }
            }
        },

        rules: {
            // 'require-jsdoc': 'off', // deprecated
            'no-unused-vars': 'off',

            '@typescript-eslint/no-unused-vars': [
                'error',
                {
                    caughtErrors: 'none'
                }
            ],

            'react/react-in-jsx-scope': 'off',
            'react/no-unescaped-entities': 'off',
            'react-hooks/rules-of-hooks': 'off',
            'react-hooks/exhaustive-deps': 'off',
            'storybook/hierarchy-separator': 'off'
        }
    }
];
