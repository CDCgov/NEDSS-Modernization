import { asSex } from './asSex';

describe('when mapping a sex demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            asOf: '04/13/2017',
            current: { value: 'current-sex-value', name: 'current-sex-name' }
        };

        const actual = asSex(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the current sex', () => {
        const demographic = {
            asOf: '04/13/2017',
            current: { value: 'current-sex-value', name: 'current-sex-name' }
        };

        const actual = asSex(demographic);

        expect(actual).toEqual(expect.objectContaining({ current: 'current-sex-value' }));
    });

    it('should include the unknown reason', () => {
        const demographic = {
            asOf: '04/13/2017',
            unknownReason: { value: 'unknown-reason-value', name: 'unknown-reason-name' }
        };

        const actual = asSex(demographic);

        expect(actual).toEqual(expect.objectContaining({ unknownReason: 'unknown-reason-value' }));
    });

    it('should include the current sex', () => {
        const demographic = {
            asOf: '04/13/2017',
            transgenderInformation: { value: 'transgender-information-value', name: 'transgender-information-name' }
        };

        const actual = asSex(demographic);

        expect(actual).toEqual(expect.objectContaining({ transgenderInformation: 'transgender-information-value' }));
    });

    it('should include the additional gender', () => {
        const demographic = {
            asOf: '04/13/2017',
            additionalGender: 'additional-gender'
        };

        const actual = asSex(demographic);

        expect(actual).toEqual(expect.objectContaining({ additionalGender: 'additional-gender' }));
    });

    it('should not map when only as of is present', () => {
        const demographic = {
            asOf: '04/13/2017'
        };

        const actual = asSex(demographic);

        expect(actual).toBeUndefined();
    });
});
