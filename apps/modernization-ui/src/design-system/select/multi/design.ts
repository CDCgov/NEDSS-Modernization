import { Theme } from 'react-select';
import { CSSObjectWithLabel } from 'react-select';

const theme = (existing: Theme) => ({
    ...existing,
    borderRadius: 2,
    colors: { ...existing.colors, primary: '#d9e8f6' }
});

const styles = {
    control: (baseStyles: CSSObjectWithLabel) => ({
        ...baseStyles,
        borderStyle: 'none',
        boxShadow: 'none',
        minHeight: 'unset',
        height: 'unset'
    })
};

export { theme, styles };
