import { validatePhoneNumber } from './PhoneValidation';

describe('Phone Validation', () => {
    it('should allow phone number with dashes and area code', () => {
        const actual = validatePhoneNumber('123-456-7890');

        expect(actual).toBeTruthy();
    });

    it('should allow phone number with spaces and area code', () => {
        const actual = validatePhoneNumber('123 456 7890');

        expect(actual).toBeTruthy();
    });

    it('should allow phone number with dashes and area code in parenthesis', () => {
        const actual = validatePhoneNumber('(123)-456-7890');

        expect(actual).toBeTruthy();
    });

    it('should allow phone number with spaces and area code in parenthesis', () => {
        const actual = validatePhoneNumber('(123) 456 7890');

        expect(actual).toBeTruthy();
    });

    it('should allow from 1 to 10 consecutive digits', () => {
        expect(validatePhoneNumber('1234567')).toBeTruthy();
        expect(validatePhoneNumber('12345678')).toBeTruthy();
        expect(validatePhoneNumber('123456789')).toBeTruthy();
        expect(validatePhoneNumber('1234567890')).toBeTruthy();
    });

    it('should allow an empty string', () => {
        const actual = validatePhoneNumber('');

        expect(actual).toBeTruthy();
    });

    it('should not allow letters', () => {
        const actual = validatePhoneNumber('aaa');

        expect(actual).toBeFalsy();
    });

    it('should not allow a string of spaces', () => {
        const actual = validatePhoneNumber('        ');

        expect(actual).toBeFalsy();
    });
});
