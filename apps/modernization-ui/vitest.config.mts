import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';
import viteConfig from './vite.config.mjs';
import tsconfigPaths from 'vite-tsconfig-paths'

const viteConfigResult = typeof viteConfig === 'function' ? viteConfig({ mode: 'test', command: 'serve' }) : viteConfig;

const alias = viteConfigResult?.resolve?.alias || {};

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
