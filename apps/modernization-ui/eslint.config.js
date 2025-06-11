// eslint.config.js - migrated from .eslintrc.json for ESLint v9+
import js from '@eslint/compat';
export default [
    js.config({
        env: { browser: true, es2021: true, jest: true },
        globals: { JSX: 'readonly' },
        extends: [
            'google',
            'eslint:recommended',
            'plugin:react/recommended',
            'plugin:prettier/recommended',
            'plugin:react-hooks/recommended',
            'plugin:storybook/recommended'
        ],
        parser: '@typescript-eslint/parser',
        parserOptions: { ecmaFeatures: { jsx: true }, ecmaVersion: 'latest' },
        plugins: ['react', '@typescript-eslint'],
        rules: {
            'require-jsdoc': 'off',
            'no-unused-vars': 'off',
            '@typescript-eslint/no-unused-vars': ['error', { caughtErrors: 'none', varsIgnorePattern: '_' }],
            'react/react-in-jsx-scope': 'off',
            'react/no-unescaped-entities': 'off',
            'react-hooks/rules-of-hooks': 'off',
            'react-hooks/exhaustive-deps': 'off',
            'valid-jsdoc': 'off'
        }
    })
];
