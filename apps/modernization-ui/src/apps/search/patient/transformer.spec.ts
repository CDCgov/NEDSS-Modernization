import { RecordStatus } from 'generated/graphql/schema';
import { PatientCriteriaEntry } from './criteria';
import { transform } from './transformer';

describe('when the PatientCriteria contains Basic Information criteria', () => {
    it('should transform with status', () => {
        const input: PatientCriteriaEntry = {
            status: [{ name: 'Inactive', label: 'Inactive', value: 'INACTIVE' }]
        };

        const actual = transform(input);

        expect(actual).toEqual(
            expect.objectContaining({ recordStatus: expect.arrayContaining([RecordStatus.Inactive]) })
        );
    });

    it('should disable soundex when not including similar', () => {
        const input: PatientCriteriaEntry = {
            includeSimilar: false,
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ disableSoundex: true }));
    });

    it('should enable soundex when including similar', () => {
        const input: PatientCriteriaEntry = {
            includeSimilar: true,
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ disableSoundex: false }));
    });

    it('should transform with last name', () => {
        const input: PatientCriteriaEntry = {
            lastName: { equals: 'last-name-value' },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { last: { equals: 'last-name-value' } } }));
    });

    it('should transform with first name', () => {
        const input: PatientCriteriaEntry = {
            firstName: { equals: 'first-name-value' },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { first: { equals: 'first-name-value' } } }));
    });

    it('should transform with gender', () => {
        const input: PatientCriteriaEntry = {
            gender: { name: 'Female', label: 'Female', value: 'F' },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ gender: 'F' }));
    });

    it('should transform with DOB', () => {
        const input: PatientCriteriaEntry = {
            bornOn: { equals: { day: 5, month: 2, year: 1995 } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(
            expect.objectContaining({
                bornOn: {
                    equals: {
                        day: 5,
                        month: 2,
                        year: 1995
                    }
                }
            })
        );
    });

    it('should transform with patient id', () => {
        const input: PatientCriteriaEntry = {
            id: 'id-value',
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ id: 'id-value' }));
    });
});

describe('when the PatientCriteria contains Address criteria', () => {
    it('should transform with Street address', () => {
        const input: PatientCriteriaEntry = {
            address: 'address-value',
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ address: 'address-value' }));
    });

    it('should transform with City', () => {
        const input: PatientCriteriaEntry = {
            city: 'city-value',
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ city: 'city-value' }));
    });

    it('should transform with State', () => {
        const input: PatientCriteriaEntry = {
            state: { name: 'State', label: 'State', value: 'state-value' },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ state: 'state-value' }));
    });

    it('should transform with Zip', () => {
        const input: PatientCriteriaEntry = {
            zip: 1051,
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ zip: '1051' }));
    });
});

describe('when the PatientCriteria contains Contact criteria', () => {
    it('should transform with phone number', () => {
        const input: PatientCriteriaEntry = {
            phoneNumber: 'phone-number-value',
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ phoneNumber: 'phone-number-value' }));
    });

    it('should transform with email', () => {
        const input: PatientCriteriaEntry = {
            email: 'email-value',
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ email: 'email-value' }));
    });
});

describe('when the PatientCriteria contains Race / Ethnicity criteria', () => {
    it('should transform with race', () => {
        const input: PatientCriteriaEntry = {
            race: { name: 'Race', label: 'Race', value: 'race-value' },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ race: 'race-value' }));
    });

    it('should transform with ethnicity', () => {
        const input: PatientCriteriaEntry = {
            ethnicity: { name: 'Ethnicity', label: 'Ethnicity', value: 'ethnicity-value' },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ ethnicity: 'ethnicity-value' }));
    });
});

describe('when the PatientCriteria contains Identification criteria', () => {
    it('should transform with identification', () => {
        const input: PatientCriteriaEntry = {
            identification: 'identification-value',
            identificationType: {
                name: 'Identification Type',
                label: 'Identification Type',
                value: 'identification-type-value'
            },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(
            expect.objectContaining({
                identification: expect.objectContaining({
                    identificationNumber: 'identification-value',
                    identificationType: 'identification-type-value'
                })
            })
        );
    });
});
