import { asPhoneEmail } from './asPhoneEmail';

describe('when mapping a phone email entry to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-phone-email' },
            use: { value: 'use-value', name: 'use-phone-email' }
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the type', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-phone-email' },
            use: { value: 'use-value', name: 'use-phone-email' }
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ type: 'type-value' }));
    });

    it('should include the use', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' }
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ use: 'use-value' }));
    });

    it('should include the country code', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            countryCode: 'country-code-value'
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ countryCode: 'country-code-value' }));
    });

    it('should include the phone number', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            phoneNumber: 'phone-number-value'
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ phoneNumber: 'phone-number-value' }));
    });

    it('should include the extension', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            extension: 'extension-value'
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ extension: 'extension-value' }));
    });

    it('should include the email', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            email: 'email-value'
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ email: 'email-value' }));
    });

    it('should include the url', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            url: 'url-value'
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ url: 'url-value' }));
    });

    it('should include the comment', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            comment: 'comment-value'
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toEqual(expect.objectContaining({ comment: 'comment-value' }));
    });

    it('should not map when type is null', () => {
        const entry = {
            asOf: '04/13/2017',
            type: null,
            use: { value: 'use-value', name: 'use-value' }
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toBeUndefined();
    });

    it('should not map when use is null', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-value' },
            use: null
        };

        const actual = asPhoneEmail(entry);

        expect(actual).toBeUndefined();
    });
});
