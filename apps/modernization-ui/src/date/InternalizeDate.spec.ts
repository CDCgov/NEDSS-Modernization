import { internalizeDate } from './InternalizeDate';

describe('when a date value is given', () => {
    it('should transform an ISO 8601 formatted date string into a string formatted as M/d/y', () => {
        expect(internalizeDate('2025-04-06')).toBe('04/06/2025');
    });

    it('should transform an ISO 8601 formatted date time string into a string formatted as M/d/y', () => {
        expect(internalizeDate('2025-04-06T11:13:19.010Z')).toBe('04/06/2025');
    });

    it('should not transform a null date', () => {
        const actual = internalizeDate(null);

        expect(actual).toBeNull();
    });
});
