import { asSelectableGender } from './options';

describe('when resolving a Selectable gender', () => {
    it('should resolve Male for M', () => {
        const actual = asSelectableGender('M');

        expect(actual).toEqual(expect.objectContaining({ name: 'Male' }));
    });

    it('should resolve Female for F', () => {
        const actual = asSelectableGender('F');

        expect(actual).toEqual(expect.objectContaining({ name: 'Female' }));
    });

    it('should resolve Unknown for U', () => {
        const actual = asSelectableGender('U');

        expect(actual).toEqual(expect.objectContaining({ name: 'Unknown' }));
    });

    it('should resolve Unknown for unknown value', () => {
        const actual = asSelectableGender('any value');

        expect(actual).toEqual(expect.objectContaining({ name: 'Unknown' }));
    });

    it('should resolve Unknown for undefined', () => {
        const actual = asSelectableGender(undefined);

        expect(actual).toEqual(expect.objectContaining({ name: 'Unknown' }));
    });

    it('should resolve Unknown for null', () => {
        const actual = asSelectableGender(null);

        expect(actual).toEqual(expect.objectContaining({ name: 'Unknown' }));
    });
});
