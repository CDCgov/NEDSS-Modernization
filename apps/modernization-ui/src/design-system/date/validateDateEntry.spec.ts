import { internalizeDate } from 'date';
import { validateDateEntry } from './validateDateEntry';
import { add } from 'date-fns';

const mockNow = jest.fn();

vi.mock('./clock', () => ({
    now: () => mockNow()
}));

describe('when validating a date entered in parts', () => {
    beforeEach(() => {
        mockNow.mockReturnValue(new Date('2020-01-25T00:00:00'));
    });

    describe('with a month', () => {
        it('should allow a valid month', () => {
            const actual = validateDateEntry('Date with valid month')({ month: 5 });

            expect(actual).toBe(true);
        });

        it('should not allow a month greater than 12', () => {
            const actual = validateDateEntry('Date with an invalid month')({ month: 13 });

            expect(actual).toContain(
                'The Date with an invalid month should have a month between 1 (January) and 12 (December).'
            );
        });

        it('should not allow a month less than 1', () => {
            const actual = validateDateEntry('Date with an invalid month')({ month: 0 });

            expect(actual).toContain(
                'The Date with an invalid month should have a month between 1 (January) and 12 (December).'
            );
        });

        it('should not allow a day greater than the days in the month', () => {
            const actual = validateDateEntry('Date with too many days')({ year: 2000, month: 2, day: 30 });

            expect(actual).toContain('The Date with too many days should have at most 29 days.');
        });

        it('should not allow months after today', () => {
            const today = new Date('2017-05-23T00:00:00');

            mockNow.mockReturnValue(new Date('2017-05-23T00:00:00'));

            const actual = validateDateEntry('Date in the future')({
                month: 6,
                year: 2017
            });

            expect(actual).toContain('The Date in the future cannot be after 05/23/2017.');
        });
    });

    describe('with a day', () => {
        it('should allow a valid day', () => {
            const actual = validateDateEntry('Date with valid day')({ day: 5 });

            expect(actual).toBe(true);
        });

        it('should not allow a day less than 1', () => {
            const actual = validateDateEntry('Date with an invalid day')({ day: 0 });

            expect(actual).toContain('The Date with an invalid day should be at least the first day of the month.');
        });

        it('should not allow a day greater than 31', () => {
            const actual = validateDateEntry('Date with an invalid day')({ day: 32 });

            expect(actual).toContain('The Date with an invalid day should have at most 31 days.');
        });
    });

    describe('with a year', () => {
        it('should allow a valid year', () => {
            const actual = validateDateEntry('Date with valid year')({ year: 1955 });

            expect(actual).toBe(true);
        });

        it('should not allow dates before the year 1875', () => {
            const actual = validateDateEntry('Date with an invalid year')({ year: 1874 });

            expect(actual).toContain('The Date with an invalid year should occur after 12/31/1874');
        });

        it('should not allow dates after this year', () => {
            const today = new Date('2021-08-19');

            mockNow.mockReturnValue(today);
            const tomorrow = add(today, { years: 1 });

            const actual = validateDateEntry('Date in the future')({
                year: tomorrow.getFullYear()
            });

            expect(actual).toContain('The Date in the future should occur before or within the current year.');
        });
    });

    describe('with a year, month, and day', () => {
        it('should not allow future dates', () => {
            mockNow.mockReturnValue(new Date('2021-08-19T00:00:00'));

            const next = {
                year: 2021,
                month: 8,
                day: 20
            };

            const actual = validateDateEntry('Date in the future')(next);

            expect(actual).toContain('The Date in the future cannot be after 08/19/2021');
        });
    });
});
