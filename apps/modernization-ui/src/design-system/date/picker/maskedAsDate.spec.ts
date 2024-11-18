import { maskedAsDate } from './maskedAsDate';

describe('when masking text as a date', () => {
    describe('and entering a full value in the MM/DD/YYYY format', () => {
        it('should display the full date', () => {
            const actual = maskedAsDate('11/13/2017');

            expect(actual).toEqual('11/13/2017');
        });

        it('should mask a full numeric value into a date', () => {
            const actual = maskedAsDate('11132017');

            expect(actual).toEqual('11/13/2017');
        });
    });

    describe('and entering a partial date value', () => {
        it.each([
            ['1113201', '11/13/201'],
            ['111320', '11/13/20'],
            ['11132', '11/13/2'],
            ['1113', '11/13'],
            ['111', '11/1'],
            ['11', '11'],
            ['1', '1'],
            ['11/13/201', '11/13/201'],
            ['11/13/20', '11/13/20'],
            ['11/13/2', '11/13/2'],
            ['11/13', '11/13'],
            ['11/1', '11/1'],
            ['11', '11'],
            ['1', '1']
        ])('should mask %s into %s', (value, expected) => {
            const actual = maskedAsDate(value);

            expect(actual).toEqual(expected);
        });
    });

    describe('and entering values not in the MM/DD/YYYY format', () => {
        it('should mask out letters', () => {
            const actual = maskedAsDate('adadas');

            expect(actual).toEqual('');
        });
    });
});
