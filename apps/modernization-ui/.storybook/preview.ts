import type { Preview } from '@storybook/react-vite';
import 'styles/global.scss';

const preview: Preview = {
    parameters: {
        controls: {
            matchers: {
                color: /(background|color)$/i,
                date: /Date$/i
            }
        },
        options: {
            storySort: (a, b) => (a.title === b.title ? 0 : a.id.localeCompare(b.id, undefined, { numeric: true }))
        }
    },
    tags: ['autodocs']
};

export default preview;
