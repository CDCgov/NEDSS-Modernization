import { asLocalDate } from './asLocalDate';

describe('given a string containing a UTC date in the ISO-8601 format', () => {
    const date = '2023-01-17T00:00:00Z';

    it('should create a Date in the local timezone with the date values of the UTC date', () => {
        const actual = asLocalDate(date);

        //  global test configuration sets the timezone as EST
        expect(actual).toEqual(new Date('2023-01-17T05:00:00Z'));
    });
});
