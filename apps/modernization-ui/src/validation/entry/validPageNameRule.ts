import { maxLengthRule } from './maxLengthRule';

/**
 * Creates a standardized react-hook-form rule for the entry of name values.
 */
const validPageNameRule = {
    ...maxLengthRule(),
    pattern: {
        value: /^[\w*()_+\-=;:/.,\d\s]+$/gm,
        message: 'Only alphabets, digits, and some special character allowed such as ( ) _ + - = ; : / . ,'
    }
};

export { validPageNameRule };
