import { maxLengthRule } from './maxLengthRule';

/**
 * Creates a standardized react-hook-form rule for the entry of name values.
 *
 * @param {string} name The name of the field.
 * @return {MaxLengthRule}
 */
const validateExtendedNameRule = (name?: string) => ({
    ...maxLengthRule(50, name)
});

export { validateExtendedNameRule };
