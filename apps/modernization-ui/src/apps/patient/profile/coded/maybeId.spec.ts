import { maybeId, maybeIds } from './maybeId';

describe('given a patietn coded value', () => {
    it('should return the identifier of the coded value', () => {
        const value = { id: 'id-value', description: 'description-value' };

        const actual = maybeId(value);

        expect(actual).toEqual('id-value');
    });

    it('should return null for a null coded value', () => {
        const actual = maybeId(null);

        expect(actual).toBeNull();
    });
});

describe('given multiple patient coded values', () => {
    it('should return the identifiers of the coded values', () => {
        const values = [
            { id: 'id-value-one', description: 'description-value-one' },
            { id: 'id-value-two', description: 'description-value-two' },
            { id: 'id-value-three', description: 'description-value-three' }
        ];

        const actual = maybeIds(values);

        expect(actual).toEqual(expect.arrayContaining(['id-value-one', 'id-value-two', 'id-value-three']));
    });
});
