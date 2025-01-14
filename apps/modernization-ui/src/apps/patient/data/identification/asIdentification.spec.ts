import { asIdentification } from './asIdentification';

describe('when mapping an identification entry to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            id: 'id-value'
        };

        const actual = asIdentification(entry);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the type', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            id: 'id-value'
        };

        const actual = asIdentification(entry);

        expect(actual).toEqual(expect.objectContaining({ type: 'type-value' }));
    });

    it('should include the id', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            id: 'id-value'
        };

        const actual = asIdentification(entry);

        expect(actual).toEqual(expect.objectContaining({ id: 'id-value' }));
    });

    it('should include the issuer', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            id: 'id-value',
            issuer: { value: 'issuer-value', name: 'issuer-type' }
        };

        const actual = asIdentification(entry);

        expect(actual).toEqual(expect.objectContaining({ id: 'id-value' }));
    });

    it('should not map when type is null', () => {
        const entry = {
            asOf: '04/13/2017',
            type: null,
            id: 'id-value'
        };

        const actual = asIdentification(entry);

        expect(actual).toBeUndefined();
    });
});
