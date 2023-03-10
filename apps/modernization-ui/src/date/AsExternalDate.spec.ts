import { asExternalDate } from './AsExternalDate';

describe('when a date value is given', () => {
    it('should transform a string formatted as M/d/y to the ISO-8601 Instant standard.', () => {
        expect(asExternalDate('5/6/2025')).toBe('2025-05-06T05:00:00.000Z');
        expect(asExternalDate('11/6/2025')).toBe('2025-11-06T05:00:00.000Z');
        expect(asExternalDate('11/16/2025')).toBe('2025-11-16T05:00:00.000Z');
    });

    it('should transform a Date in the local timezone to the ISO-8601 Instant standard,', () => {
        expect(asExternalDate(new Date('2025-5-6'))).toBe('2025-05-06T05:00:00.000Z');
    });

    it('should transform a UTC Date to the ISO-8601 Instant standard,', () => {
        expect(asExternalDate(new Date('2025-05-06'))).toBe('2025-05-06T00:00:00.000Z');
    });

    it('should not transform a null date', () => {
        const actual = asExternalDate(null);

        expect(actual).toBeNull();
    });
});
