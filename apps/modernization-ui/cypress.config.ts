import { defineConfig } from 'cypress';

export default defineConfig({
    screenshotOnRunFailure: true,
    component: {
        devServer: {
            framework: 'create-react-app',
            bundler: 'webpack'
        },
        experimentalInteractiveRunEvents: true
    }
});
