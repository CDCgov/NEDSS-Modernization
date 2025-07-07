import { differenceInYears } from 'date-fns';
import { asDate, displayAgeAsOf, displayAgeAsOfToday } from './displayAge';

describe('displayAgeAsOf', () => {
    it('should display age in years', () => {
        const actual = displayAgeAsOf('1984-09-01', new Date('2023-01-01'));

        expect(actual).toEqual('38 years');
    });

    it('should display 1 year', () => {
        const actual = displayAgeAsOf('1984-09-01', new Date('1985-09-01'));

        expect(actual).toEqual('1 year');
    });

    it('should display age in months', () => {
        const actual = displayAgeAsOf(new Date('2022-10-01'), new Date('2022-12-01'));

        expect(actual).toEqual('2 months');
    });

    it('should display 1 month', () => {
        const actual = displayAgeAsOf(new Date('2022-12-01'), new Date('2023-01-01'));

        expect(actual).toEqual('1 month');
    });

    it('should display age in days', () => {
        const actual = displayAgeAsOf(new Date('2022-01-01'), new Date('2022-01-22'));

        expect(actual).toEqual('21 days');
    });

    it('should display 1 day', () => {
        const actual = displayAgeAsOf(new Date('2022-01-01'), new Date('2022-01-02'));

        expect(actual).toEqual('1 day');
    });

    it('should return undefined when date is undefined', () => {
        const actual = displayAgeAsOf(undefined, new Date('2022-01-02'));

        expect(actual).toBeUndefined();
    });

    it('should return undefined when date is invalid', () => {
        const actual = displayAgeAsOf('bad date', new Date('2022-01-02'));

        expect(actual).toBeUndefined();
    });

    it('should return undefined when date is after from', () => {
        const actual = displayAgeAsOf(new Date('2022-02-01'), new Date('2022-01-02'));

        expect(actual).toBeUndefined();
    });

    it('should display 1 year when from is string', () => {
        const actual = displayAgeAsOf('1984-09-01', '1985-09-01');

        expect(actual).toEqual('1 year');
    });

    it('should display age in years when from is string', () => {
        const actual = displayAgeAsOf('1984-09-01', '2023-01-01');

        expect(actual).toEqual('38 years');
    });

    it('should display age in months when from is string', () => {
        const actual = displayAgeAsOf('2022-10-01', '2022-12-01');

        expect(actual).toEqual('2 months');
    });
});

describe('displayAgeAsOfToday', () => {
    const birthDate = new Date('2021-02-01');
    const actual = displayAgeAsOfToday(birthDate);
    const expected = `${differenceInYears(new Date(), birthDate)} years`;
    expect(actual).toEqual(expected);
});

describe('asDate', () => {
    it('should convert a valid date string to a Date object', () => {
        const dateString = '2023-10-01';
        const result = asDate(dateString);
        expect(result).toEqual(new Date(dateString));
    });

    it('should convert a Date object to a Date object', () => {
        const dateObject = new Date('2023-10-01');
        const result = asDate(dateObject);
        expect(result).toEqual(dateObject);
    });

    it('should return undefined for an invalid date string', () => {
        const invalidDateString = 'invalid-date';
        const result = asDate(invalidDateString);
        expect(result).toBeUndefined();
    });

    it('should return undefined for an invalid Date object', () => {
        const invalidDateObject = new Date('invalid-date');
        const result = asDate(invalidDateObject);
        expect(result).toBeUndefined();
    });
});
