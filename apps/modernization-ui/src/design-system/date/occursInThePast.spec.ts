import { add } from 'date-fns';

import { internalizeDate } from 'date';

import { occursInThePast } from './occursInThePast';

const mockNow = vi.fn();

vi.mock('./clock', () => ({
    now: () => mockNow(),
}));

describe('when validating that a DateEntry occurs in the past', () => {
    it('should allow dates before today', () => {
        const today = new Date('1997-10-03');

        mockNow.mockReturnValue(today);

        const actual = occursInThePast('Date field name')({
            year: 1990,
            month: 12,
            day: 19,
        });

        expect(actual).toBe(true);
    });

    it('should allow dates without a year', () => {
        const today = new Date('1997-10-03');

        mockNow.mockReturnValue(today);

        const actual = occursInThePast('Date field name')({
            year: null,
            month: 12,
            day: 19,
        });

        expect(actual).toBe(true);
    });

    it('should not allow future dates', () => {
        const today = new Date('1967-07-13');

        mockNow.mockReturnValue(today);

        const tomorrow = add(today, { days: 1 });

        const actual = occursInThePast('Date field name')({
            year: tomorrow.getFullYear(),
            month: tomorrow.getMonth() + 1,
            day: tomorrow.getDate(),
        });

        expect(actual).toContain(`The Date field name cannot be after ${internalizeDate(today)}`);
    });

    it('should not allow future dates with month and year only', () => {
        const today = new Date('1967-07-13');

        mockNow.mockReturnValue(today);

        const tomorrow = add(today, { months: 1 });

        const actual = occursInThePast('Date field name')({
            year: tomorrow.getFullYear(),
            month: tomorrow.getMonth() + 1,
            day: null,
        });

        expect(actual).toContain(`The Date field name cannot be after ${internalizeDate(today)}`);
    });
});
