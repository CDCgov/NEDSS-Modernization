import { Theme } from 'react-select';

const theme = (existing: Theme) => ({
    ...existing,
    borderRadius: 2,
    colors: { ...existing.colors, primary: '#d9e8f6' }
});

export { theme };
