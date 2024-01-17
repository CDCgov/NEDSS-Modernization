import { maxLengthRule } from './maxLengthRule';

/**
 * Creates a standardized react-hook-form rule for the entry of name values.
 */
const validNameRule = {
    ...maxLengthRule()
};

export { validNameRule };
