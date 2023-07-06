import { validate } from './SearchValidator';

describe('Phone Search Validation', () => {
    it('should allow partial search of area code', () => {
        expect(validate('123-')).toBeTruthy();

        expect(validate('(123)')).toBeTruthy();

        expect(validate('(123) ')).toBeTruthy();

        expect(validate('123 ')).toBeTruthy();
    });

    it('should allow partial search of phone number', () => {
        expect(validate('(123) 456')).toBeTruthy();

        expect(validate('(123)-456')).toBeTruthy();

        expect(validate('123-456')).toBeTruthy();

        expect(validate('123 456')).toBeTruthy();
    });

    it('should allow phone number with dashes and area code', () => {
        const actual = validate('123-456-7890');

        expect(actual).toBeTruthy();
    });

    it('should allow phone number with spaces and area code', () => {
        const actual = validate('123 456 7890');

        expect(actual).toBeTruthy();
    });

    it('should allow phone number with dashes and area code in parenthesis', () => {
        const actual = validate('(123)-456-7890');

        expect(actual).toBeTruthy();
    });

    it('should allow phone number with spaces and area code in parenthesis', () => {
        const actual = validate('(123) 456 7890');

        expect(actual).toBeTruthy();
    });

    it('should allow from 1 to 10 consecutive digits', () => {
        expect(validate('123456')).toBeTruthy();
        expect(validate('1234567')).toBeTruthy();
        expect(validate('12345678')).toBeTruthy();
        expect(validate('123456789')).toBeTruthy();
        expect(validate('1234567890')).toBeTruthy();
    });

    it('should allow an empty string', () => {
        const actual = validate('');

        expect(actual).toBeTruthy();
    });

    it('should not allow letters', () => {
        const actual = validate('aaa');

        expect(actual).toBeFalsy();
    });

    it('should not allow a string of spaces', () => {
        const actual = validate('        ');

        expect(actual).toBeFalsy();
    });
});
