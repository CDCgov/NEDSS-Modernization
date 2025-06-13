import js from '@eslint/js';
import react from 'eslint-plugin-react';
import tseslint from '@typescript-eslint/eslint-plugin';
import prettier from 'eslint-plugin-prettier';
import reactHooks from 'eslint-plugin-react-hooks';
import storybook from 'eslint-plugin-storybook';
import jsdoc from 'eslint-plugin-jsdoc';
import { defineConfig } from 'eslint/config';
import globals from 'globals';
import tsParser from '@typescript-eslint/parser';

export default defineConfig([
    // Main config
    {
        files: ['**/*.{js,jsx,ts,tsx}'],
        ignores: [
            'build/**',
            'node_modules/**',
            '**/generated/**',
            'src/setupTests.js',
            'src/setupProxy.js',
            'src/codegen.ts'
        ],
        languageOptions: {
            parser: tsParser,
            ecmaVersion: 'latest',
            sourceType: 'module',
            parserOptions: {
                ecmaFeatures: { jsx: true }
            },
            globals: {
                ...globals.browser,
                ...globals.jest,
                JSX: 'readonly'
            }
        },
        plugins: {
            react,
            '@typescript-eslint': tseslint,
            prettier,
            'react-hooks': reactHooks,
            storybook,
            jsdoc
        },
        /* extends: ['jsdoc/recommended'],*/
        rules: {
            ...js.configs.recommended.rules,
            ...jsdoc.configs['flat/requirements-typescript'].rules,
            'prettier/prettier': 'error',
            'no-console': 'warn',
            'require-jsdoc': 'off',
            'jsdoc/require-jsdoc': 'off',
            'jsdoc/require-example': 'off',
            'jsdoc/require-param': 'off',
            'jsdoc/require-returns': 'off',
            'no-unused-vars': 'off',
            '@typescript-eslint/no-unused-vars': ['error', { caughtErrors: 'none' }],
            'react/react-in-jsx-scope': 'off',
            'react/no-unescaped-entities': 'off',
            'react-hooks/rules-of-hooks': 'off',
            'react-hooks/exhaustive-deps': 'off',
            'storybook/hierarchy-separator': 'off'
        },
        settings: {
            react: { version: 'detect' },
            jsdoc: {
                tagNamePreference: {
                    return: 'return'
                }
            }
        }
    },
    // Test files overrides
    {
        files: ['**/*.spec.{js,jsx,ts,tsx}', '**/*.test.{js,jsx,ts,tsx}'],
        languageOptions: {
            globals: {
                vi: 'readonly'
            }
        },
        rules: {
            '@typescript-eslint/no-unused-vars': 'off',
            'no-undef': 'off',
            'no-unused-vars': 'off',
            'prettier/prettier': 'off'
        }
    }
]);
