type MaxLengthRule = {
    maxLength: {
        value: number;
        message: string;
    };
};

/**
 * Creates a standardized react-hook-form rule for the maximum input length.
 *
 * @param {number} value The maximum character length, defaults to 50.
 * @param {string} name The name of the field, defaults to 'Field'.
 * @return {MaxLengthRule}
 */
const maxLengthRule = (value = 50, name?: string): MaxLengthRule => ({
    maxLength: { value, message: name ? `${name}: Only allows ${value} characters` : `Only allows ${value} characters` }
});

export { maxLengthRule };
