import { externalizeDate } from './ExternalizeDate';

describe('when a date value is given', () => {
    it('should transform a string formatted as M/d/y to the ISO-8601 Date standard.', () => {
        expect(externalizeDate('5/6/2025')).toBe('2025-05-06');
        expect(externalizeDate('11/6/2025')).toBe('2025-11-06');
        expect(externalizeDate('11/16/2025')).toBe('2025-11-16');
    });

    it('should transform a Date in the local timezone to the ISO-8601 Date standard,', () => {
        expect(externalizeDate(new Date('2025-5-6'))).toBe('2025-05-06');
    });

    it('should transform a UTC Date to the ISO-8601 Date standard,', () => {
        expect(externalizeDate(new Date(2025, 4, 6))).toBe('2025-05-06');
    });

    it('should not transform a null date', () => {
        const actual = externalizeDate(null);

        expect(actual).toBeNull();
    });
});
