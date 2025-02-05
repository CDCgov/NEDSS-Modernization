import { filterResolver } from './filterResolver';

describe('when resolving a PatientSearchFilter', () => {
    it('should resolve undefined when there are no properties', () => {
        const actual = filterResolver({});

        expect(actual).toBe(undefined);
    });

    it('should resolve undefined when there are no known properties', () => {
        const actual = filterResolver({
            some: 'some',
            other: 'other',
            unknown: 'unknown'
        });

        expect(actual).toBe(undefined);
    });

    it('should resolve id when present', () => {
        const actual = filterResolver({
            id: 'filter-value'
        });

        expect(actual).toEqual({ id: 'filter-value' });
    });

    it('should resolve address when present', () => {
        const actual = filterResolver({
            address: 'filter-value'
        });

        expect(actual).toEqual({ address: 'filter-value' });
    });

    it('should resolve ageOrDateOfBirth when present', () => {
        const actual = filterResolver({
            ageOrDateOfBirth: 'filter-value'
        });

        expect(actual).toEqual({ ageOrDateOfBirth: 'filter-value' });
    });

    it('should resolve email when present', () => {
        const actual = filterResolver({
            email: 'filter-value'
        });

        expect(actual).toEqual({ email: 'filter-value' });
    });

    it('should resolve name when present', () => {
        const actual = filterResolver({
            name: 'filter-value'
        });

        expect(actual).toEqual({ name: 'filter-value' });
    });

    it('should resolve sex when present', () => {
        const actual = filterResolver({
            sex: 'filter-value'
        });

        expect(actual).toEqual({ sex: 'filter-value' });
    });

    it('should resolve phone when present', () => {
        const actual = filterResolver({
            phone: 'filter-value'
        });

        expect(actual).toEqual({ phone: 'filter-value' });
    });

    it('should resolve identification when present', () => {
        const actual = filterResolver({
            identification: 'filter-value'
        });

        expect(actual).toEqual({ identification: 'filter-value' });
    });
});
