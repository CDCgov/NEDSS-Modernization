import { optionSelectValidator } from './OptionSelectFilter';

describe('OptionSelectFilter', () => {
    describe('optionSelectValidator', () => {
        const MOCK_FILTER = {
            reportFilterUid: 1001,
            filterType: {
                id: 5,
                codeTable: undefined,
                descTxt: 'State List',
                code: 'J_S01',
                filterCodeSetName: undefined,
                filterType: 'BAS_JUR_LIST',
                filterName: 'States',
            },
            isRequired: true,
            reportColumnUid: 2001,
        };
        const MOCK_LABEL = 'Full Name';

        it('passes valid single value', () => {
            const validator = optionSelectValidator(MOCK_FILTER, MOCK_LABEL);

            const result = validator('01');
            expect(result).toBeTruthy();
        });

        it('passes valid multi value', () => {
            const validator = optionSelectValidator(MOCK_FILTER, MOCK_LABEL);

            const result = validator(['01', '02']);
            expect(result).toBeTruthy();
        });

        it('fails when required and empty', () => {
            const validator = optionSelectValidator(MOCK_FILTER, MOCK_LABEL);
            const errStr = 'The Full Name is required.';

            expect(validator([])).toEqual(errStr);
            expect(validator(undefined)).toEqual(errStr);
        });

        it('passes when not required and empty', () => {
            const validator = optionSelectValidator({ ...MOCK_FILTER, isRequired: false }, MOCK_LABEL);

            expect(validator([])).toBeTruthy();
            expect(validator(undefined)).toBeTruthy();
        });
    });
});
