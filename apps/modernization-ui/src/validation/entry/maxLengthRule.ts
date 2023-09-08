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
 * @return {MaxLengthRule}
 */
const maxLengthRule = (value = 50): MaxLengthRule => ({
    maxLength: { value, message: `Only allows ${value} characters` }
});

export { maxLengthRule };
