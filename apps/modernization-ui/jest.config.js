// jest.config.js
module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'jsdom',
    collectCoverage: true,
    collectCoverageFrom: ['src/*/.{js,jsx,ts,tsx}', '!src/*/.d.ts'],
    coverageThreshold: {
        global: {
            branches: 75,
            functions: 75,
            lines: 75,
            statements: 75
        }
    }
};
