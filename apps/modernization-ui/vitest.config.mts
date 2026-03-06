import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';
import tsconfigPaths from 'vite-tsconfig-paths'


export default defineConfig({
    plugins: [react(), tsconfigPaths()],
    test: {
        environment: 'jsdom',
        globals: true,
        setupFiles: ['./src/setupTests.ts'],
        clearMocks: true, // <-- This will clear all mocks before each test, like Jest's clearMocks
        testTimeout: 10000, // Set global test timeout to 10s
        watch: false,
        coverage: {
            reporter: ['text', 'json', 'html']
        },
        css: {
            modules: {
                classNameStrategy: 'non-scoped'
            }
        }
    }
});
