import { monthYearRangeValidator } from './MonthYearRangeFilter';

describe('MonthYearRangeFilter', () => {
    describe('monthYearRangeValidator', () => {
        const MOCK_FILTER = {
            reportFilterUid: 1001,
            filterType: {
                id: 5,
                codeTable: undefined,
                descTxt: 'Basic Time Filter for Time Range accepts MM;YYYY to MM;YYYY',
                code: 'T_T01',
                filterCodeSetName: undefined,
                filterType: 'BAS_MM_YYYY_RANGE',
                filterName: 'Time Range',
            },
            isRequired: true,
            reportColumnUid: 2001,
        };
        const MOCK_LABEL = 'Full Name';

        it('passes valid values', () => {
            const validator = monthYearRangeValidator(MOCK_FILTER, MOCK_LABEL);

            const result = validator(['01/2024', '01/2025']);
            expect(result).toBeTruthy();
        });

        it('fails when required and empty', () => {
            const validator = monthYearRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = 'The Full Name is required.';

            expect(validator(['', ''])).toEqual(errStr);
            expect(validator([undefined, undefined])).toEqual(errStr);
            expect(validator(undefined)).toEqual(errStr);
        });

        it('passes when not required and empty', () => {
            const validator = monthYearRangeValidator({ ...MOCK_FILTER, isRequired: false }, MOCK_LABEL);

            expect(validator(['', ''])).toBeTruthy();
            expect(validator([undefined, undefined])).toBeTruthy();
            expect(validator(undefined)).toBeTruthy();
        });

        it('fails when partially empty', () => {
            const validator = monthYearRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = 'Both From and To dates must be populated';

            expect(validator(['01/2024', ''])).toEqual(errStr);
            expect(validator([undefined, '01/2024'])).toEqual(errStr);
            expect(validator([undefined, '42/01/2024'])).toEqual(errStr);
        });

        it('fails invalid From dates', () => {
            const validator = monthYearRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = `From field must have both Month and Year populated`;

            expect(validator(['00/2027', '01/2026'])).toEqual(errStr);
            expect(validator(['01/0', '01/2026'])).toEqual(errStr);
        });

        it('fails invalid To dates', () => {
            const validator = monthYearRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = `To field must have both Month and Year populated`;

            expect(validator(['01/2026', '00/2024'])).toEqual(errStr);
            expect(validator(['01/2026', '01/0'])).toEqual(errStr);
        });

        it('fails out of order dates', () => {
            const validator = monthYearRangeValidator(MOCK_FILTER, MOCK_LABEL);

            expect(validator(['01/2025', '01/2024'])).toEqual('From date must be before To date');
        });
    });
});
