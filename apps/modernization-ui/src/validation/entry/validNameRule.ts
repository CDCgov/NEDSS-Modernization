import { maxLengthRule } from './maxLengthRule';

/**
 * Creates a standardized react-hook-form rule for the entry of name values.
 */
const validNameRule = {
    ...maxLengthRule(),
    pattern: { value: /^[a-zA-ZÀ-ÿ-']+$/, message: 'Names can not contain numbers or special characters.' }
};

export { validNameRule };
