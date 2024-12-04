import { internalizeDate } from 'date';
import { validateDateEntry } from './validateDateEntry';
import { add } from 'date-fns';

const mockNow = jest.fn();

jest.mock('./clock', () => ({
    now: () => mockNow()
}));

describe('when validating a date entered in parts', () => {
    beforeEach(() => mockNow.mockReturnValue(new Date()));

    describe('with a month', () => {
        it('should allow a valid month', () => {
            const actual = validateDateEntry('Date with valid month')({ month: 5 });

            expect(actual).toBe(true);
        });

        it('should not allow a month greater than 12', () => {
            const actual = validateDateEntry('Date with an invalid month')({ month: 13 });

            expect(actual).toContain(
                `The Date with an invalid month should have a month between 1 (January) and 12 (December)`
            );
        });

        it('should not allow a month less than 1', () => {
            const actual = validateDateEntry('Date with an invalid month')({ month: 0 });

            expect(actual).toContain(
                `The Date with an invalid month should have a month between 1 (January) and 12 (December)`
            );
        });

        it('should not allow a day greater than the days in the month', () => {
            const actual = validateDateEntry('Date with too many days')({ year: 2000, month: 2, day: 30 });

            expect(actual).toContain(`The Date with too many days should have at most 29 days`);
        });

        it('should not allow months after today', () => {
            const today = new Date('2021-08-19');

            mockNow.mockReturnValue(today);

            const tomorrow = add(today, { months: 1 });

            const actual = validateDateEntry('Date in the future')({
                month: tomorrow.getMonth() + 1,
                year: tomorrow.getFullYear()
            });

            expect(actual).toContain(`The Date in the future cannot be after ${internalizeDate(today)}`);
        });
    });

    describe('with a day', () => {
        it('should allow a valid day', () => {
            const actual = validateDateEntry('Date with valid day')({ day: 5 });

            expect(actual).toBe(true);
        });

        it('should not allow a day less than 1', () => {
            const actual = validateDateEntry('Date with an invalid day')({ day: 0 });

            expect(actual).toContain(`The Date with an invalid day should be at least the first day of the month`);
        });

        it('should not allow a day greater than 31', () => {
            const actual = validateDateEntry('Date with an invalid day')({ day: 32 });

            expect(actual).toContain(`The Date with an invalid day should have at most 31 days`);
        });
    });

    describe('with a year', () => {
        it('should allow a valid year', () => {
            const actual = validateDateEntry('Date with valid year')({ year: 1955 });

            expect(actual).toBe(true);
        });

        it('should not allow dates before the year 1875', () => {
            const actual = validateDateEntry('Date with an invalid year')({ year: 1874 });

            expect(actual).toContain(`The Date with an invalid year should occur after 12/31/1874`);
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
            const today = new Date();

            const tomorrow = add(today, { days: 1 });

            const next = {
                year: tomorrow.getFullYear(),
                month: tomorrow.getMonth() + 1,
                day: tomorrow.getDate()
            };

            const actual = validateDateEntry('Date in the future')(next);

            expect(actual).toContain(`The Date in the future cannot be after ${internalizeDate(today)}`);
        });
    });
});
