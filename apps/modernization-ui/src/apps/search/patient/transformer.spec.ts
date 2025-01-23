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

    it('should transform with last name', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { equals: 'last-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { last: { equals: 'last-name-value' } } }));
    });

    it('should transform with last name with contains', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { contains: 'last-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { last: { contains: 'last-name-value' } } }));
    });

    it('should transform with last name with not equal', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { not: 'last-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { last: { not: 'last-name-value' } } }));
    });

    it('should transform with last name with sounds like', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { soundsLike: 'last-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { last: { soundsLike: 'last-name-value' } } }));
    });

    it('should transform with last name with starts with', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { startsWith: 'last-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { last: { startsWith: 'last-name-value' } } }));
    });

    it('should transform with first name', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { equals: 'first-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { first: { equals: 'first-name-value' } } }));
    });

    it('should transform with first name with contains', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { contains: 'first-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { first: { contains: 'first-name-value' } } }));
    });

    it('should transform with first name with starts with', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { startsWith: 'first-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { first: { startsWith: 'first-name-value' } } }));
    });

    it('should transform with first name with sounds like', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { soundsLike: 'first-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { first: { soundsLike: 'first-name-value' } } }));
    });

    it('should transform with first name with not equal', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { not: 'first-name-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ name: { first: { not: 'first-name-value' } } }));
    });

    it('should transform with gender', () => {
        const input: PatientCriteriaEntry = {
            gender: { name: 'Female', label: 'Female', value: 'F' },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ gender: 'F' }));
    });

    it('should transform with DOB equals', () => {
        const input: PatientCriteriaEntry = {
            bornOn: { equals: { month: 2, day: 5, year: 1995 } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ bornOn: { equals: { month: 2, day: 5, year: 1995 } } }));
    });

    it('should transform with DOB between', () => {
        const input: PatientCriteriaEntry = {
            bornOn: { between: { from: '02/05/1995', to: '02/05/1995' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(
            expect.objectContaining({ bornOn: { between: { from: '02/05/1995', to: '02/05/1995' } } })
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
            location: { street: { equals: 'address-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ location: { street: { equals: 'address-value' } } }));
    });

    it('should transform with Street address with contains', () => {
        const input: PatientCriteriaEntry = {
            location: { street: { contains: 'address-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ location: { street: { contains: 'address-value' } } }));
    });

    it('should transform with Street address with not equal', () => {
        const input: PatientCriteriaEntry = {
            location: { street: { not: 'address-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ location: { street: { not: 'address-value' } } }));
    });

    it('should transform with City', () => {
        const input: PatientCriteriaEntry = {
            location: { city: { equals: 'city-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ location: { city: { equals: 'city-value' } } }));
    });

    it('should transform with City', () => {
        const input: PatientCriteriaEntry = {
            location: { city: { contains: 'city-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ location: { city: { contains: 'city-value' } } }));
    });

    it('should transform with City', () => {
        const input: PatientCriteriaEntry = {
            location: { city: { not: 'city-value' } },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(expect.objectContaining({ location: { city: { not: 'city-value' } } }));
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

describe('when the PatientCriteria contains filter criteria', () => {
    it('should transform with patient id', () => {
        const input: PatientCriteriaEntry = {
            filter: {
                patientid: 'patient-id-value'
            },
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(
            expect.objectContaining({
                filter: {
                    id: 'patient-id-value'
                }
            })
        );
    });
});

describe('when the PatientCriteria contains event id criteria', () => {
    it.each([
        'morbidity',
        'investigation',
        'vaccination',
        'treatment',
        'abcCase',
        'cityCountyCase',
        'notification',
        'labReport',
        'stateCase',
        'document',
        'accessionNumber'
    ])('should transform with %s', (type) => {
        const input: PatientCriteriaEntry = {
            [type]: '1234',
            status: []
        };

        const actual = transform(input);

        expect(actual).toEqual(
            expect.objectContaining({
                [type]: '1234'
            })
        );
    });
});
