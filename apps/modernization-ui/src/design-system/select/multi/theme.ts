import { Theme } from 'react-select';

const theme = (existing: Theme) => ({
    ...existing,
    borderRadius: 2,
    colors: { ...existing.colors, primary: '#DEE7F5', primary25: '#' }
});

export { theme };
