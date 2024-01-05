import { maxLengthRule } from './maxLengthRule';

/**
 * Creates a standardized react-hook-form rule for the entry of name values.
 */
const validPageNameRule = {
    ...maxLengthRule(),
    pattern: {
        value: /^[\w*()+\-=;:/.,\s]+$/gm,
        message: 'Valid characters are A-Z, a-z, 0-9, or * ( ) _ + - = ; : / . ,'
    }
};

export { validPageNameRule };
