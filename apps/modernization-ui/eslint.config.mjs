import js from '@eslint/js';
import react from 'eslint-plugin-react';
import tseslint from '@typescript-eslint/eslint-plugin';
import reactHooks from 'eslint-plugin-react-hooks';
import storybook from 'eslint-plugin-storybook';
import jsdoc from 'eslint-plugin-jsdoc';
import { defineConfig } from 'eslint/config';
import globals from 'globals';
import tsParser from '@typescript-eslint/parser';
import importPlugin from 'eslint-plugin-import';

export default defineConfig([
    // Main config
    {
        files: ['**/*.{js,jsx,ts,tsx}'],
        ignores: ['build/**', 'node_modules/**', '**/generated/**', 'src/setupProxy.js', 'src/codegen.ts'],
        languageOptions: {
            parser: tsParser,
            ecmaVersion: 'latest',
            sourceType: 'module',
            parserOptions: {
                ecmaFeatures: { jsx: true },
            },
            globals: {
                ...globals.browser,
                ...globals.jest,
                JSX: 'readonly',
            },
        },
        plugins: {
            react,
            '@typescript-eslint': tseslint,
            'react-hooks': reactHooks,
            storybook,
            jsdoc,
            import: importPlugin,
        },
        rules: {
            ...tseslint.configs.recommended.rules,
            ...js.configs.recommended.rules,
            ...jsdoc.configs['flat/requirements-typescript'].rules,
            'no-console': 'warn',
            'require-jsdoc': 'off',
            'jsdoc/require-jsdoc': 'off',
            'jsdoc/require-example': 'off',
            'jsdoc/require-param': 'off',
            'jsdoc/require-returns': 'off',
            'max-len': ['warn', { code: 120 }],
            'no-unused-vars': 'off',
            '@typescript-eslint/no-unused-vars': [
                'error',
                { caughtErrors: 'none', destructuredArrayIgnorePattern: '^_' },
            ],
            'react/react-in-jsx-scope': 'off',
            'react/no-unescaped-entities': 'off',
            'react-hooks/rules-of-hooks': 'warn',
            // KLUDGE: this should be on, but doesn't play well with some of the current patterns
            'react-hooks/exhaustive-deps': 'off',
            'react/jsx-curly-brace-presence': [2, 'never'],
            'react/jsx-boolean-value': [2, 'always'],
            'dot-notation': 'error',
            'object-shorthand': 'error',
            eqeqeq: 'error',
            'no-var': 'error',
            'prefer-const': 'error',
            'storybook/hierarchy-separator': 'off',
            'object-shorthand': 'error',

            'import/export': 'error',
            'import/no-empty-named-blocks': 'error',
            'import/no-extraneous-dependencies': 'error',
            'import/no-named-as-default-member': 'error',
            'import/no-named-as-default': 'error',
            'import/no-unused-modules': ['error', { unusedExports: true, missingExports: true }],
            'import/default': 'error',
            'import/named': 'error',
            'import/namespace': 'error',
            'import/no-unresolved': 'error',
            'import/no-useless-path-segments': 'error',
            'import/first': 'error',
            'import/group-exports': 'error',
            'import/no-unassigned-import': 'error',
        },
        settings: {
            react: { version: 'detect' },
            jsdoc: {
                tagNamePreference: {
                    return: 'return',
                },
            },
        },
    },
    // Test and story files overrides
    {
        files: [
            '**/*.spec.{js,jsx,ts,tsx}',
            '**/*.test.{js,jsx,ts,tsx}',
            '**/*.stories.{js,jsx,ts,tsx}',
            'src/setupTests.ts',
        ],
        languageOptions: {
            globals: {
                vi: 'readonly',
            },
        },
        rules: {
            ...tseslint.configs.recommended.rules,
            ...js.configs.recommended.rules,
            '@typescript-eslint/no-explicit-any': 'off',
            '@typescript-eslint/no-unused-vars': 'off',
            'react-hooks/rules-of-hooks': 'off',
            'no-undef': 'off',
            'no-unused-vars': 'off',
            'no-console': 'off',
            'max-len': 'off',
        },
    },
]);
