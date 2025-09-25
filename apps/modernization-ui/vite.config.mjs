import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';
import { resolve } from 'path';

export default defineConfig(({ mode }) => {
    // note: Will load ports from .env file in root (of the project)
     
    const env = loadEnv(mode, process.cwd(), '');
    const NBS_API_PORT = env.VITE_NBS_API_PORT || 8080;
    const DEDUPLICATION_PORT = env.VITE_DEDUPLICATION_PORT || 8083;
    const PAGE_BUILDER_PORT = env.VITE_PAGE_BUILDER_PORT || 8095;

    return {
        appType: 'spa',
        plugins: [react()],
        publicDir: 'public',
        resolve: {
            alias: {
                '@': resolve('src'),
                address: resolve('src/address'),
                alert: resolve('src/alert'),
                analytics: resolve('src/analytics'),
                apps: resolve('src/apps'),
                authorization: resolve('src/authorization'),
                classic: resolve('src/classic'),
                components: resolve('src/components'),
                'conditional-render': resolve('src/conditional-render'),
                configuration: resolve('src/configuration'),
                config: resolve('src/config.js'),
                cryptography: resolve('src/cryptography'),
                'design-system': resolve('src/design-system'),
                date: resolve('src/date'),
                feature: resolve('src/feature'),
                filters: resolve('src/filters'),
                generated: resolve('src/generated'),
                layout: resolve('src/layout'),
                libs: resolve('src/libs'),
                logout: resolve('src/logout'),
                mixins: resolve('src/mixins'),
                name: resolve('src/name'),
                navigation: resolve('src/navigation'),
                options: resolve('src/options'),
                page: resolve('src/page'),
                pages: resolve('src/pages'),
                pagination: resolve('src/pagination'),
                providers: resolve('src/providers'),
                routes: resolve('src/routes'),
                shared: resolve('src/shared'),
                SkipLink: resolve('src/SkipLink'),
                storage: resolve('src/storage'),
                styles: resolve('src/styles'),
                suggestion: resolve('src/suggestion'),
                system: resolve('src/system'),
                user: resolve('src/user'),
                utils: resolve('src/utils'),
                validation: resolve('src/validation'),
                breadcrumb: resolve('src/breadcrumb'),
                confirmation: resolve('src/confirmation'),
                overlay: resolve('src/overlay'),
                coded: resolve('src/coded')
            }
        },
        server: {
            port: 3000,
            open: true,
            proxy: {
                '/nbs/api/deduplication': { target: `http://localhost:${DEDUPLICATION_PORT}`, changeOrigin: true },
                '/nbs/api': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true },
                '/graphql': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true },
                '/encryption': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true },
                '/nbs/page-builder/api': { target: `http://localhost:${PAGE_BUILDER_PORT}`, changeOrigin: true },
                '/login': { target: `http://localhost:${NBS_API_PORT}`, changeOrigin: true }
            }
        },
        build: { outDir: 'build', rollupOptions: { input: 'public/index.html' } }
    };
});
