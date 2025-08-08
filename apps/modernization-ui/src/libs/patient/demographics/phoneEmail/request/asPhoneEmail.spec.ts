import { asPhoneEmail } from './asPhoneEmail';

describe('when mapping a phone email demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-phone-email' },
            use: { value: 'use-value', name: 'use-phone-email' }
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the type', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-phone-email' },
            use: { value: 'use-value', name: 'use-phone-email' }
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ type: 'type-value' }));
    });

    it('should include the use', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' }
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ use: 'use-value' }));
    });

    it('should include the country code', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            countryCode: 'country-code-value'
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ countryCode: 'country-code-value' }));
    });

    it('should include the phone number', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            phoneNumber: 'phone-number-value'
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ phoneNumber: 'phone-number-value' }));
    });

    it('should include the extension', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            extension: 'extension-value'
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ extension: 'extension-value' }));
    });

    it('should include the email', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            email: 'email-value'
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ email: 'email-value' }));
    });

    it('should include the url', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            url: 'url-value'
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ url: 'url-value' }));
    });

    it('should include the comment', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            comment: 'comment-value'
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toEqual(expect.objectContaining({ comment: 'comment-value' }));
    });

    it('should not map when type is null', () => {
        const demographic = {
            asOf: '04/13/2017',
            use: { value: 'use-value', name: 'use-value' }
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toBeUndefined();
    });

    it('should not map when use is null', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-value' }
        };

        const actual = asPhoneEmail(demographic);

        expect(actual).toBeUndefined();
    });
});
