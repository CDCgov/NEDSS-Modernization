import { asLocalDate } from './asLocalDate';

describe('given a string containing a UTC date in the ISO-8601 format', () => {
    it('should create a Date in the local timezone with the date values of the UTC date', () => {
        const date = '2023-01-17T00:00:00Z';
        const actual = asLocalDate(date);
        //  global test configuration sets the timezone as EST
        expect(actual).toEqual(new Date('2023-01-17T05:00:00Z'));
    });

    it('should create a Date in the local timezone with the date values of the UTC date, adjusting for daylight savings', () => {
        const date = '2023-07-17T00:00:00Z';
        const actual = asLocalDate(date);
        //  global test configuration sets the timezone as EST
        expect(actual).toEqual(new Date('2023-07-17T04:00:00Z'));
    });

    it('it should return Invalid Date for an invalid date string', () => {
        const date = 'invalid-date-string';
        const actual = asLocalDate(date);
        expect(actual).toEqual(new Date('Invalid Date'));
    });
});
