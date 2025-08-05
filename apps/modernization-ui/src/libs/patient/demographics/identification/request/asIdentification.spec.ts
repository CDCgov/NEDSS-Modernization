import { asIdentification } from './asIdentification';

describe('when mapping an identification demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            value: 'id-value'
        };

        const actual = asIdentification(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the type', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            value: 'id-value'
        };

        const actual = asIdentification(demographic);

        expect(actual).toEqual(expect.objectContaining({ type: 'type-value' }));
    });

    it('should include the id', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            value: 'id-value'
        };

        const actual = asIdentification(demographic);

        expect(actual).toEqual(expect.objectContaining({ value: 'id-value' }));
    });

    it('should include the issuer', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            value: 'id-value',
            issuer: { value: 'issuer-value', name: 'issuer-type' }
        };

        const actual = asIdentification(demographic);

        expect(actual).toEqual(expect.objectContaining({ value: 'id-value' }));
    });

    it('should not map when type is null', () => {
        const demographic = {
            asOf: '04/13/2017',
            value: 'id-value'
        };

        const actual = asIdentification(demographic);

        expect(actual).toBeUndefined();
    });
});
