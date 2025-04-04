import type { Preview } from '@storybook/react';
import 'styles/global.scss';

// v7-style sort
function storySort(a, b) {
    return a.title === b.title
      ? 0
      : a.id.localeCompare(b.id, undefined, { numeric: true });
}

const preview: Preview = {
    parameters: {
        controls: {
            matchers: {
                color: /(background|color)$/i,
                date: /Date$/i
            }
        },
        options: {
            storySort: (a, b) => a.title === b.title ? 0 : a.id.localeCompare(b.id, undefined, { numeric: true })
        }
    }
};

export default preview;
