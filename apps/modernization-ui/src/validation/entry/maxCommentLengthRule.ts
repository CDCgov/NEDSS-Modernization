type MaxCommentLengthRule = {
    maxLength: {
        value: number;
        message: string;
    };
};
/**
 * Creates a standardized react-hook-form rule for the maximum comment input length.
 *
 * @param {number} value The maximum character length, defaults to 2000.
 * @param {string} name The name of the field, defaults to 'Field'.
 * @return {MaxCommentLengthRule}
 */
const maxCommentLengthRule = (value = 2000, name?: string): MaxCommentLengthRule => ({
    maxLength: {
        value,
        message: name ? `The ${name} only allows ${value} characters max.` : `Only allows ${value} characters max.`
    }
});

export { maxCommentLengthRule };
