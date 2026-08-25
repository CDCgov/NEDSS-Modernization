import js from '@eslint/js';
import react from 'eslint-plugin-react';
import tseslint from '@typescript-eslint/eslint-plugin';
import reactHooks from 'eslint-plugin-react-hooks';
import storybook from 'eslint-plugin-storybook';
import jsdoc from 'eslint-plugin-jsdoc';
import { defineConfig } from 'eslint/config';
import globals from 'globals';
import tsParser from '@typescript-eslint/parser';
import eslintPluginImport from 'eslint-plugin-import';

const baseRules = {
    ...tseslint.configs.recommended.rules,
    ...js.configs.recommended.rules,
    'no-console': 'warn',
    'max-len': ['warn', { code: 120 }],
    'no-unused-vars': 'off',
    '@typescript-eslint/no-unused-vars': ['error', { caughtErrors: 'none', destructuredArrayIgnorePattern: '^_' }],
    'react/react-in-jsx-scope': 'off',
    'react/no-unescaped-entities': 'off',
    'react-hooks/rules-of-hooks': 'warn',
    // KLUDGE: this should really be on, but it's a bigger project to update everything and make sure correct
    'react-hooks/exhaustive-deps': 'off',
    'react/jsx-curly-brace-presence': [2, 'never'],
    'react/jsx-boolean-value': [2, 'always'],
    'dot-notation': 'error',
    eqeqeq: 'error',
    'no-var': 'error',
    'prefer-const': 'error',
    'storybook/hierarchy-separator': 'off',
    'object-shorthand': 'error',

    'import/default': 'error',
    'import/export': 'error',
    'import/first': 'error',
    'import/named': 'error',
    'import/namespace': 'error',
    'import/no-empty-named-blocks': 'error',
    'import/no-extraneous-dependencies': 'error',
    'import/no-named-as-default-member': 'error',
    'import/no-named-as-default': 'error',
    'import/no-unassigned-import': ['error', { allow: ['**/*.scss', '@testing-library/**/*', 'jest-axe/**/*'] }],
    'import/no-useless-path-segments': 'error',
    // Sort outer import statement lines
    'import/order': [
        'error',
        {
            groups: ['builtin', 'external', 'internal', 'parent', 'sibling'],

            pathGroups: [
                {
                    pattern: 'react',
                    group: 'builtin',
                    position: 'before',
                },
            ],

            pathGroupsExcludedImportTypes: [],
            'newlines-between': 'always',

            alphabetize: {
                order: 'asc',
                caseInsensitive: false,
            },

            distinctGroup: false,
        },
    ],
    // Sort members within a single import statement
    'sort-imports': [
        'error',
        {
            ignoreCase: true,
            ignoreDeclarationSort: true, // Let import/order handle statement lines
            ignoreMemberSort: false,
            memberSyntaxSortOrder: ['none', 'all', 'multiple', 'single'],
        },
    ],
};

const jsDocRules = {
    ...jsdoc.configs['flat/requirements-typescript'].rules,
    'require-jsdoc': 'off',
    'jsdoc/require-jsdoc': 'off',
    'jsdoc/require-example': 'off',
    'jsdoc/require-param': 'off',
    'jsdoc/require-returns': 'off',
};

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
            import: eslintPluginImport,
        },
        rules: {
            ...baseRules,
            ...jsDocRules,
        },
        settings: {
            react: { version: 'detect' },
            jsdoc: {
                tagNamePreference: {
                    return: 'return',
                },
            },
            'import/parsers': {
                '@typescript-eslint/parser': ['.ts', '.tsx'],
            },
            'import/resolver': {
                typescript: {
                    alwaysTryTypes: true,
                    project: './tsconfig.json',
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
            ...baseRules,
            '@typescript-eslint/no-explicit-any': 'off',
            '@typescript-eslint/no-unused-vars': 'off',
            'react-hooks/rules-of-hooks': 'off',
            'react-hooks/exhaustive-deps': 'off',
            'no-undef': 'off',
            'no-unused-vars': 'off',
            'no-console': 'off',
            'max-len': 'off',
        },
    },
]);
