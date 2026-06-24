import { validateDateRange, validateNumericRange } from './rangeValidator.ts';

describe('rangeValidator', () => {
    const FIELD_NAME = 'patient_birth_date';
    describe('validateDateRange', () => {
        it('should not return an error msg for a valid date range', () => {
            const actual = validateDateRange(['10/11/2024', '09/04/2026'], FIELD_NAME);
            expect(actual).toBeUndefined();
        });

        it('should return an error msg for missing date range', () => {
            const actual0 = validateDateRange(['', ''], FIELD_NAME);
            expect(actual0).toEqual(`Enter from and to values for ${FIELD_NAME}.`);

            const actual1 = validateDateRange(['', '2026'], FIELD_NAME);
            expect(actual1).toEqual(`Enter from and to values for ${FIELD_NAME}.`);

            const actual2 = validateDateRange(['12/2025', ''], FIELD_NAME);
            expect(actual2).toEqual(`Enter from and to values for ${FIELD_NAME}.`);
        });

        it('should return an error msg for an invalid date range', () => {
            const actual0 = validateDateRange(['14/1999', '10/13/2026'], FIELD_NAME);
            expect(actual0).toEqual(`From date of "14/1999" is not valid mm/dd/yyyy formatted date for ${FIELD_NAME}.`);

            const actual1 = validateDateRange(['10/13/2022', 1], FIELD_NAME);
            expect(actual1).toEqual(`To date of "1" is not valid mm/dd/yyyy formatted date for ${FIELD_NAME}.`);

            const actual2 = validateDateRange(['10/13/2022', '1/1/1992323'], FIELD_NAME);
            expect(actual2).toEqual(
                `To date of "1/1/1992323" is not valid mm/dd/yyyy formatted date for ${FIELD_NAME}.`
            );
        });

        it('should return an error msg when the from date is greater than the to date', () => {
            const actual0 = validateDateRange(['10/15/2026', '10/13/2026'], FIELD_NAME);
            expect(actual0).toEqual(`From date must be before to date for ${FIELD_NAME}.`);
        });
    });

    describe('validateNumericRange', () => {
        it('should not return an error msg for a valid numeric', () => {
            const actual0 = validateNumericRange(['90210', '100000'], FIELD_NAME);
            expect(actual0).toBeUndefined();

            const actual1 = validateNumericRange([-1, 1.0], FIELD_NAME);
            expect(actual1).toBeUndefined();
        });

        it('should return an error msg for missing numeric range', () => {
            const actual0 = validateNumericRange(['', ''], FIELD_NAME);
            expect(actual0).toEqual(`Enter from and to values for ${FIELD_NAME}.`);

            const actual1 = validateNumericRange(['', '1'], FIELD_NAME);
            expect(actual1).toEqual(`Enter from and to values for ${FIELD_NAME}.`);

            const actual2 = validateNumericRange(['2', ''], FIELD_NAME);
            expect(actual2).toEqual(`Enter from and to values for ${FIELD_NAME}.`);
        });

        it('should return an error msg for an invalid numeric range', () => {
            const actual0 = validateNumericRange(['abc', '1'], FIELD_NAME);
            expect(actual0).toEqual(`From value of "abc" is not a valid number for ${FIELD_NAME}.`);

            const actual1 = validateNumericRange(['2323', '10/13/2022'], FIELD_NAME);
            expect(actual1).toEqual(`To value of "10/13/2022" is not a valid number for ${FIELD_NAME}.`);
        });

        it('should return an error msg when the from number is greater than the to number', () => {
            const actual0 = validateNumericRange([15, 6], FIELD_NAME);
            expect(actual0).toEqual(`From value must be before to value for ${FIELD_NAME}.`);
        });
    });
});
