import { asAgeDisplay } from './asAgeDisplay';

describe('given a value', () => {
    it('should display age in years', () => {
        const actual = asAgeDisplay('1984-09-01', new Date('2023-01-01'));

        expect(actual).toEqual('38 years');
    });

    it('should display 1 year', () => {
        const actual = asAgeDisplay('1984-09-01', new Date('1985-09-01'));

        expect(actual).toEqual('1 year');
    });

    it('should display age in months', () => {
        const actual = asAgeDisplay(new Date('2022-10-01'), new Date('2022-12-01'));

        expect(actual).toEqual('2 months');
    });

    it('should display 1 month', () => {
        const actual = asAgeDisplay(new Date('2022-12-01'), new Date('2023-01-01'));

        expect(actual).toEqual('1 month');
    });

    it('should display age in days', () => {
        const actual = asAgeDisplay(new Date('2022-01-01'), new Date('2022-01-22'));

        expect(actual).toEqual('21 days');
    });

    it('should display 1 day', () => {
        const actual = asAgeDisplay(new Date('2022-01-01'), new Date('2022-01-02'));

        expect(actual).toEqual('1 day');
    });

    it('should return undefined when date is undefined', () => {
        const actual = asAgeDisplay(undefined, new Date('2022-01-02'));

        expect(actual).toBeUndefined();
    });

    it('should return undefined when date is invalid', () => {
        const actual = asAgeDisplay('bad date', new Date('2022-01-02'));

        expect(actual).toBeUndefined();
    });

    it('should return undefined when date is after from', () => {
        const actual = asAgeDisplay(new Date('2022-02-01'), new Date('2022-01-02'));

        expect(actual).toBeUndefined();
    });
});
