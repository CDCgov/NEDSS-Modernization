import js from '@eslint/js';
import react from 'eslint-plugin-react';
import tseslint from '@typescript-eslint/eslint-plugin';
import prettier from 'eslint-plugin-prettier';
import reactHooks from 'eslint-plugin-react-hooks';
import storybook from 'eslint-plugin-storybook';
import { defineConfig } from 'eslint/config';
import globals from 'globals';
import tsParser from '@typescript-eslint/parser';

export default defineConfig([
    {
        files: ['**/*.{js,jsx,ts,tsx}'],
        ignores: ['build/**', 'node_modules/**', 'src/generated/**'],
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
            storybook
        },
        rules: {
            ...js.configs.recommended.rules,
            'require-jsdoc': 'off',
            'valid-jsdoc': 'off',
            'no-unused-vars': 'off',
            '@typescript-eslint/no-unused-vars': [
                'error',
                { caughtErrors: 'none' }
            ],
            'react/react-in-jsx-scope': 'off',
            'react/no-unescaped-entities': 'off',
            'react-hooks/rules-of-hooks': 'off',
            'react-hooks/exhaustive-deps': 'off',
            'storybook/hierarchy-separator': 'off'
        },
        settings: {
            react: { version: 'detect' }
        }
    }
]);
