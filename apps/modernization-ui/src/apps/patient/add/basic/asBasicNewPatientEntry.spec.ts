import { asBasicNewPatientEntry } from './asBasicNewPatientEntry';

describe('when adding a new patient from a patient search', () => {
    it('should populate the date of birth from the date equals criteria with a full date', () => {
        const criteria = { bornOn: { equals: { month: 6, day: 5, year: 2025 } } };

        const actual = asBasicNewPatientEntry(criteria);

        expect(actual).toEqual(
            expect.objectContaining({ personalDetails: expect.objectContaining({ bornOn: '06/05/2025' }) })
        );
    });

    it('should populate the date of birth from the date equals criteria with only a month', () => {
        const criteria = { bornOn: { equals: { month: 6 } } };

        const actual = asBasicNewPatientEntry(criteria);

        expect(actual.personalDetails?.bornOn).toBeUndefined();
    });

    it('should populate the date of birth from the date equals criteria with only a day', () => {
        const criteria = { bornOn: { equals: { day: 5 } } };

        const actual = asBasicNewPatientEntry(criteria);

        expect(actual.personalDetails?.bornOn).toBeUndefined();
    });

    it('should populate the date of birth from the date equals criteria with only a year', () => {
        const criteria = { bornOn: { equals: { year: 2025 } } };

        const actual = asBasicNewPatientEntry(criteria);

        expect(actual.personalDetails?.bornOn).toBeUndefined();
    });
});
