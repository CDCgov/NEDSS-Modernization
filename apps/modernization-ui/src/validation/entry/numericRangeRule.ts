type NumericRangeRule = {
    min: {
        value: number;
        message: string;
    };
    max: {
        value: number;
        message: string;
    };
};

/**
 * Creates a standardized react-hook-form rule for a range of numeric values.
 * Note: Values are inclusive.
 * @param {number} min The minimum value. Defaults to 0.
 * @param {number} max The maximum value. Defaults to 99999.
 * @return {NumericRangeRule}
 */
const numericRangeRule = (min = 0, max = 99999): NumericRangeRule => ({
    min: { value: min, message: `Must be ${min} or greater.` },
    max: { value: max, message: `Must be ${max} or less.` }
});

export { numericRangeRule };
export type { NumericRangeRule };
