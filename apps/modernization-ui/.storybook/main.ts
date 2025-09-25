import type { StorybookConfig } from '@storybook/react-vite';

const config: StorybookConfig = {
    stories: ['../src/**/*.mdx', '../src/**/*.stories.@(tsx)'],
    addons: ['@storybook/addon-onboarding', '@chromatic-com/storybook', '@storybook/addon-docs'],
    framework: {
        name: '@storybook/react-vite',
        options: {}
    },
    staticDirs: ['../public']
};
export default config;
