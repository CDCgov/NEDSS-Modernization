import { validateEmail } from './validateEmail';

describe('when validating an email entered at text', () => {
    it('should allow a value with a username and a domain', () => {
        const actual = validateEmail('valid email')('username@domain.com');
    });

    it('should not allow a value that does not contain a domain', () => {
        const actual = validateEmail('Email without an @')('value');

        expect(actual).toContain(`Please enter Email without an @ as an email address`);
    });

    it('should not allow a value that does not contain a domain after the @ symbol', () => {
        const actual = validateEmail('Email that ends with an @')('value@');

        expect(actual).toContain(`Please enter Email that ends with an @ as an email address`);
    });

    it('should not allow a value does not contain a username', () => {
        const actual = validateEmail('Email that starts with an @')('@value');

        expect(actual).toContain(`Please enter Email that starts with an @ as an email address`);
    });
});
