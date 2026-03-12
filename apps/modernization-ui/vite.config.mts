import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';
import tsconfigPaths from 'vite-tsconfig-paths';
import { resolve } from 'path';

export default defineConfig(({ mode }) => {
    // note: Will load ports from .env file in root (of the project)

    const env = loadEnv(mode, process.cwd(), '');
    const NBS_API_PORT = env.VITE_NBS_API_PORT || 8080;
    const DEDUPLICATION_PORT = env.VITE_DEDUPLICATION_PORT || 8083;
    const PAGE_BUILDER_PORT = env.VITE_PAGE_BUILDER_PORT || 8095;

    return {
        appType: 'spa',
        plugins: [react(), tsconfigPaths()],
        publicDir: 'public',
        // Only needed for scss imports
        resolve: {
            alias: {
                styles: resolve('src/styles'),
                mixins: resolve('src/mixins'),
                'design-system': resolve('src/design-system'),
            },
        },
        server: {
            port: 3000,
            proxy: {
                '/nbs/api/deduplication': { target: `http://localhost:${DEDUPLICATION_PORT}`, changeOrigin: true },
                '/nbs/api': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true },
                '/graphql': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true },
                '/encryption': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true },
                '/nbs/page-builder/api': { target: `http://localhost:${PAGE_BUILDER_PORT}`, changeOrigin: true },
                '/login': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true },
            },
        },
        build: { outDir: 'build' },
    };
});
