import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';
const __dirname = dirname(fileURLToPath(import.meta.url));
export default defineConfig({
    plugins: [react()],
    root: __dirname,
    publicDir: 'public',
    resolve: {
        alias: {
            '@': resolve(__dirname, 'src'),
            address: resolve(__dirname, 'src/address'),
            alert: resolve(__dirname, 'src/alert'),
            analytics: resolve(__dirname, 'src/analytics'),
            apps: resolve(__dirname, 'src/apps'),
            classic: resolve(__dirname, 'src/classic'),
            components: resolve(__dirname, 'src/components'),
            'conditional-render': resolve(__dirname, 'src/conditional-render'),
            configuration: resolve(__dirname, 'src/configuration'),
            config: resolve(__dirname, 'src/config.js'),
            cryptography: resolve(__dirname, 'src/cryptography'),
            'design-system': resolve(__dirname, 'src/design-system'),
            date: resolve(__dirname, 'src/date'),
            feature: resolve(__dirname, 'src/feature'),
            filters: resolve(__dirname, 'src/filters'),
            generated: resolve(__dirname, 'src/generated'),
            layout: resolve(__dirname, 'src/layout'),
            libs: resolve(__dirname, 'src/libs'),
            mixins: resolve(__dirname, 'src/mixins'),
            name: resolve(__dirname, 'src/name'),
            navigation: resolve(__dirname, 'src/navigation'),
            options: resolve(__dirname, 'src/options'),
            page: resolve(__dirname, 'src/page'),
            pagination: resolve(__dirname, 'src/pagination'),
            providers: resolve(__dirname, 'src/providers'),
            routes: resolve(__dirname, 'src/routes'),
            shared: resolve(__dirname, 'src/shared'),
            SkipLink: resolve(__dirname, 'src/SkipLink'),
            storage: resolve(__dirname, 'src/storage'),
            styles: resolve(__dirname, 'src/styles'),
            suggestion: resolve(__dirname, 'src/suggestion'),
            system: resolve(__dirname, 'src/system'),
            user: resolve(__dirname, 'src/user'),
            utils: resolve(__dirname, 'src/utils'),
            validation: resolve(__dirname, 'src/validation'),
            breadcrumb: resolve(__dirname, 'src/breadcrumb'),
            confirmation: resolve(__dirname, 'src/confirmation'),
            overlay: resolve(__dirname, 'src/overlay'),
            coded: resolve(__dirname, 'src/coded')
        }
    },
    server: {
        port: 3000,
        open: true,
        proxy: {
            '/nbs/api/deduplication': { target: 'http://localhost:8083', changeOrigin: true },
            '/nbs/api': { target: 'http://localhost:8080', changeOrigin: true },
            '/graphql': { target: 'http://localhost:8080', changeOrigin: true },
            '/encryption': { target: 'http://localhost:8080', changeOrigin: true },
            '/nbs/page-builder/api': { target: 'http://localhost:8095', changeOrigin: true },
            '/login': { target: 'http://localhost:8080', changeOrigin: true }
        }
    },
    build: { outDir: 'build', rollupOptions: { input: resolve(__dirname, 'public/index.html') } }
});
