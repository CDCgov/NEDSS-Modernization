import { dateRangeValidator } from './DateRangeFilter';

describe('DateRangeFilter', () => {
    describe('dateRangeValidator', () => {
        const MOCK_FILTER = {
            reportFilterUid: 1001,
            filterType: {
                id: 5,
                codeTable: undefined,
                descTxt: 'Basic Time Filter for Time Range accepts MM;YYYY to MM;YYYY',
                code: 'T_T01',
                filterCodeSetName: undefined,
                filterType: 'BAS_TIM_RANGE',
                filterName: 'Time Range',
            },
            isRequired: true,
            reportColumnUid: 2001,
        };
        const MOCK_LABEL = 'Full Name';

        it('passes valid values', () => {
            const validator = dateRangeValidator(MOCK_FILTER, MOCK_LABEL);

            const result = validator(['01/01/2024', '01/01/2025']);
            expect(result).toBeTruthy();
        });

        it('fails when required and empty', () => {
            const validator = dateRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = 'The Full Name is required.';

            expect(validator(['', ''])).toEqual(errStr);
            expect(validator([undefined, undefined])).toEqual(errStr);
            expect(validator(undefined)).toEqual(errStr);
        });

        it('passes when not required and empty', () => {
            const validator = dateRangeValidator({ ...MOCK_FILTER, isRequired: false }, MOCK_LABEL);

            expect(validator(['', ''])).toBeTruthy();
            expect(validator([undefined, undefined])).toBeTruthy();
            expect(validator(undefined)).toBeTruthy();
        });

        it('fails when partially empty', () => {
            const validator = dateRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = 'Both From and To dates must be populated';

            expect(validator(['01/01/2024', ''])).toEqual(errStr);
            expect(validator([undefined, '01/01/2024'])).toEqual(errStr);
            expect(validator([undefined, '42/01/2024'])).toEqual(errStr);
        });

        it('fails invalid From dates', () => {
            const validator = dateRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = (val: string) => `From date of "${val}" is not valid mm/dd/yyyy formatted date`;

            expect(validator(['16/01/2024', '01/01/2026'])).toEqual(errStr('16/01/2024'));
            expect(validator(['abc', '01/01/2026'])).toEqual(errStr('abc'));
        });

        it('fails invalid To dates', () => {
            const validator = dateRangeValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = (val: string) => `To date of "${val}" is not valid mm/dd/yyyy formatted date`;

            expect(validator(['01/01/2026', '16/01/2024'])).toEqual(errStr('16/01/2024'));
            expect(validator(['01/01/2026', 'abc'])).toEqual(errStr('abc'));
        });

        it('fails out of order dates', () => {
            const validator = dateRangeValidator(MOCK_FILTER, MOCK_LABEL);

            expect(validator(['01/01/2025', '01/01/2024'])).toEqual('From date must be before To date');
        });
    });
});
