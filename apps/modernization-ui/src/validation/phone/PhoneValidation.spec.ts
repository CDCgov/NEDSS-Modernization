import { validatePhoneNumber } from './PhoneValidation';

describe('Phone Validation', () => {
    it('should allow phone number with dashes and area code', () => {
        const actual = validatePhoneNumber('123-456-7890');

        expect(actual).toBe(true);
    });

    it('should allow phone number with spaces and area code', () => {
        const actual = validatePhoneNumber('123 456 7890');

        expect(actual).toBe(true);
    });

    it('should allow phone number with dashes and area code in parenthesis', () => {
        const actual = validatePhoneNumber('(123)-456-7890');

        expect(actual).toBe(true);
    });

    it('should allow phone number with spaces and area code in parenthesis', () => {
        const actual = validatePhoneNumber('(123) 456 7890');

        expect(actual).toBe(true);
    });

    it('should allow from 1 to 10 consecutive digits', () => {
        expect(validatePhoneNumber('1234567')).toBe(true);
        expect(validatePhoneNumber('12345678')).toBe(true);
        expect(validatePhoneNumber('123456789')).toBe(true);
        expect(validatePhoneNumber('1234567890')).toBe(true);
    });

    it('should allow an empty string', () => {
        const actual = validatePhoneNumber('');

        expect(actual).toBe(true);
    });

    it('should not allow letters', () => {
        const actual = validatePhoneNumber('aaa');

        expect(actual).toEqual('Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).');
    });

    it('should not allow a string of spaces', () => {
        const actual = validatePhoneNumber('        ');

        expect(actual).toEqual('Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).');
    });
});
