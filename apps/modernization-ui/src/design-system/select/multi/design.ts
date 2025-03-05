import { Theme } from 'react-select';
import { CSSObjectWithLabel } from 'react-select';

const theme = (existing: Theme) => ({
    ...existing,
    borderRadius: 2,
    colors: { ...existing.colors, primary: '#d9e8f6' }
});

const fontSizeWithinChips = (standardRemFontSize: number) => {
    return standardRemFontSize * (1 + 3 / 17);
};

const styles = {
    control: (baseStyles: CSSObjectWithLabel) => ({
        ...baseStyles,
        fontSize: `${fontSizeWithinChips(0.75)}rem`,
        borderStyle: 'none',
        boxShadow: 'none',
        minHeight: 'unset',
        height: 'unset'
    })
};

export { theme, styles };
