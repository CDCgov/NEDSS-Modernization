import { internalizeDate } from 'date';
import { add } from 'date-fns';
import { validateDate } from './validateDate';

const mockNow = jest.fn();

vi.mock('./clock', () => ({
    now: () => mockNow()
}));

describe('when validating a date entered at text', () => {
    beforeEach(() => {
        mockNow.mockReturnValue(new Date('2020-01-25T00:00:00'));
    });

    describe('with an invalid format', () => {
        it.each(['', 'not a date', '01152014', '1/1/2021'])(
            "should not allow a value in an incorrect format: '%s' ",
            (value) => {
                const actual = validateDate('Date with an invalid format')(value);

                expect(actual).toContain('The Date with an invalid format should be in the format MM/DD/YYYY.');
            }
        );

        it('should not allow a month greater than 12', () => {
            const actual = validateDate('Date with an invalid month')('15/31/2000');

            expect(actual).toContain(
                'The Date with an invalid month should have a month between 1 (January) and 12 (December).'
            );
        });

        it('should not allow a month less than 1', () => {
            const actual = validateDate('Date with an invalid month')('00/31/2000');

            expect(actual).toContain(
                'The Date with an invalid month should have a month between 1 (January) and 12 (December).'
            );
        });
    });

    describe('with a valid format ', () => {
        it('should not allow a day greater than the days in the month', () => {
            const actual = validateDate('Date with too many days')('02/30/2000');

            expect(actual).toContain('The Date with too many days should have at most 29 days.');
        });

        it('should not allow dates before the year 1875', () => {
            const actual = validateDate('Date with an invalid year')('12/31/1874');

            expect(actual).toContain('The Date with an invalid year should occur after 12/31/1874.');
        });

        it('should not allow future dates', () => {
            const actual = validateDate('Date field name')('01/27/2020');

            expect(actual).toContain('The Date field name cannot be after 01/25/2020.');
        });
    });
});
