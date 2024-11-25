import { PatientCriteriaEntry } from './criteria';
import { patientTermsResolver } from './patientTermsResolver';

describe('when the PatientCriteria contains Basic Information criteria', () => {
    it('should resolve terms with status', () => {
        const input: PatientCriteriaEntry = {
            status: [
                { name: 'Inactive', label: 'Inactive', value: 'INACTIVE' },
                { name: 'Active', label: 'Active', value: 'ACTIVE' }
            ]
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual([]);
    });

    it('should resolve terms', () => {
        const input: PatientCriteriaEntry = {
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual([]);
    });

    it('should resolve terms with last name', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { equals: 'last-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.last',
                    title: 'LAST NAME',
                    name: 'last-name-value',
                    value: 'last-name-value',
                    operator: 'Equals'
                }
            ])
        );
    });

    it('should resolve terms with last name with contains', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { contains: 'last-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.last',
                    title: 'LAST NAME',
                    name: 'last-name-value',
                    value: 'last-name-value',
                    operator: 'Contains'
                }
            ])
        );
    });

    it('should resolve terms with last name with not equal', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { not: 'last-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.last',
                    title: 'LAST NAME',
                    name: 'last-name-value',
                    value: 'last-name-value',
                    operator: 'Not equal'
                }
            ])
        );
    });

    it('should resolve terms with last name with starts with', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { startsWith: 'last-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.last',
                    title: 'LAST NAME',
                    name: 'last-name-value',
                    value: 'last-name-value',
                    operator: 'Starts with'
                }
            ])
        );
    });

    it('should resolve terms with last name with sounds like', () => {
        const input: PatientCriteriaEntry = {
            name: { last: { soundsLike: 'last-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.last',
                    title: 'LAST NAME',
                    name: 'last-name-value',
                    value: 'last-name-value',
                    operator: 'Sounds like'
                }
            ])
        );
    });

    it('should resolve terms with first name', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { equals: 'first-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.first',
                    title: 'FIRST NAME',
                    name: 'first-name-value',
                    value: 'first-name-value',
                    operator: 'Equals'
                }
            ])
        );
    });

    it('should resolve terms with first name with starts with', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { startsWith: 'first-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.first',
                    title: 'FIRST NAME',
                    name: 'first-name-value',
                    value: 'first-name-value',
                    operator: 'Starts with'
                }
            ])
        );
    });

    it('should resolve terms with first name with sounds like', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { soundsLike: 'first-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.first',
                    title: 'FIRST NAME',
                    name: 'first-name-value',
                    value: 'first-name-value',
                    operator: 'Sounds like'
                }
            ])
        );
    });

    it('should resolve terms with first name with not equal', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { not: 'first-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.first',
                    title: 'FIRST NAME',
                    name: 'first-name-value',
                    value: 'first-name-value',
                    operator: 'Not equal'
                }
            ])
        );
    });

    it('should resolve terms with first name with contains', () => {
        const input: PatientCriteriaEntry = {
            name: { first: { contains: 'first-name-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'name.first',
                    title: 'FIRST NAME',
                    name: 'first-name-value',
                    value: 'first-name-value',
                    operator: 'Contains'
                }
            ])
        );
    });

    it('should resolve terms with gender', () => {
        const input: PatientCriteriaEntry = {
            gender: { name: 'Female', label: 'Female', value: 'F' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'gender', title: 'SEX', name: 'Female', value: 'F' }])
        );
    });

    it('should resolve terms with patient id', () => {
        const input: PatientCriteriaEntry = {
            id: 'id-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'id', title: 'PATIENT ID', name: 'id-value', value: 'id-value', partial: true }
            ])
        );
    });

    it('should resolve multiple terms with patient id', () => {
        const input: PatientCriteriaEntry = {
            id: '123,456; 789',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'id', title: 'PATIENT ID', name: '123', value: '123', partial: true },
                { source: 'id', title: 'PATIENT ID', name: '456', value: '456', partial: true },
                { source: 'id', title: 'PATIENT ID', name: '789', value: '789', partial: true }
            ])
        );
    });
});

describe('when the PatientCriteria contains Address criteria', () => {
    it('should resolve terms with Street address', () => {
        const input: PatientCriteriaEntry = {
            location: { street: { equals: 'address-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'location.street',
                    title: 'STREET ADDRESS',
                    name: 'address-value',
                    value: 'address-value',
                    operator: 'Equals'
                }
            ])
        );
    });

    it('should resolve terms with Street address with not equals', () => {
        const input: PatientCriteriaEntry = {
            location: { street: { not: 'address-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'location.street',
                    title: 'STREET ADDRESS',
                    name: 'address-value',
                    value: 'address-value',
                    operator: 'Not equal'
                }
            ])
        );
    });

    it('should resolve terms with Street address with contains', () => {
        const input: PatientCriteriaEntry = {
            location: { street: { contains: 'address-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'location.street',
                    title: 'STREET ADDRESS',
                    name: 'address-value',
                    value: 'address-value',
                    operator: 'Contains'
                }
            ])
        );
    });

    it('should resolve terms with City', () => {
        const input: PatientCriteriaEntry = {
            location: { city: { equals: 'city-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'location.city', title: 'CITY', name: 'city-value', value: 'city-value', operator: 'Equals' }
            ])
        );
    });

    it('should resolve terms with City', () => {
        const input: PatientCriteriaEntry = {
            location: { city: { not: 'city-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'location.city', title: 'CITY', name: 'city-value', value: 'city-value', operator: 'Not equal' }
            ])
        );
    });

    it('should resolve terms with City', () => {
        const input: PatientCriteriaEntry = {
            location: { city: { contains: 'city-value' } },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'location.city', title: 'CITY', name: 'city-value', value: 'city-value', operator: 'Contains' }
            ])
        );
    });

    it('should resolve terms with State', () => {
        const input: PatientCriteriaEntry = {
            state: { name: 'State Name', label: 'State Label', value: 'state-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'state', title: 'STATE', name: 'State Name', value: 'state-value' }])
        );
    });

    it('should resolve terms with Zip', () => {
        const input: PatientCriteriaEntry = {
            zip: 1051,
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'zip', title: 'ZIP CODE', name: '1051', value: '1051' }])
        );
    });
});

describe('when the PatientCriteria contains Contact criteria', () => {
    it('should resolve terms with phone number', () => {
        const input: PatientCriteriaEntry = {
            phoneNumber: 'phone-number-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'phoneNumber', title: 'PHONE', name: 'phone-number-value', value: 'phone-number-value' }
            ])
        );
    });

    it('should resolve terms with email', () => {
        const input: PatientCriteriaEntry = {
            email: 'email-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'email', title: 'EMAIL', name: 'email-value', value: 'email-value' }])
        );
    });
});

describe('when the PatientCriteria contains Race / Ethnicity criteria', () => {
    it('should resolve terms with race', () => {
        const input: PatientCriteriaEntry = {
            race: { name: 'Race Name', label: 'Race Label', value: 'race-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'race', title: 'RACE', name: 'Race Name', value: 'race-value' }])
        );
    });

    it('should resolve terms with ethnicity', () => {
        const input: PatientCriteriaEntry = {
            ethnicity: { name: 'Ethnicity Name', label: 'Ethnicity Label', value: 'ethnicity-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'ethnicity', title: 'ETHNICITY', name: 'Ethnicity Name', value: 'ethnicity-value' }
            ])
        );
    });
});

describe('when the PatientCriteria contains Identification criteria', () => {
    it('should resolve terms with identification', () => {
        const input: PatientCriteriaEntry = {
            identification: 'identification-value',
            identificationType: {
                name: 'Identification Type Name',
                label: 'Identification Type Label',
                value: 'identification-type-value'
            },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'identificationType',
                    title: 'ID TYPE',
                    name: 'Identification Type Name',
                    value: 'identification-type-value'
                },
                { source: 'identification', title: 'ID', name: 'identification-value', value: 'identification-value' }
            ])
        );
    });
});

type EventIdValue = { source: string; title: string; name: string; value: string };

describe('when the PatientCriteria contains event ids criteria', () => {
    it.each<EventIdValue>([
        { name: '1234', source: 'morbidity', title: 'MORBIDITY REPORT ID', value: '1234' },
        { name: '1234', source: 'investigation', title: 'INVESTIGATION ID', value: '1234' },
        { name: '1234', source: 'vaccination', title: 'VACCINATION ID', value: '1234' },
        { name: '1234', source: 'treatment', title: 'TREATMENT ID', value: '1234' },
        { name: '1234', source: 'abcCase', title: 'ABCS CASE ID', value: '1234' },
        { name: '1234', source: 'cityCountyCase', title: 'CITY/COUNTY CASE ID', value: '1234' },
        { name: '1234', source: 'notification', title: 'NOTIFICATION ID', value: '1234' },
        { name: '1234', source: 'labReport', title: 'LAB ID', value: '1234' },
        { name: '1234', source: 'stateCase', title: 'STATE CASE ID', value: '1234' },
        { name: '1234', source: 'document', title: 'DOCUMENT ID', value: '1234' },
        { name: '1234', source: 'accessionNumber', title: 'ACCESSION NUMBER ID', value: '1234' }
    ])('should resolve terms with %s %s', ({ name, source, title, value }) => {
        const input: PatientCriteriaEntry = {
            [source]: '1234',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.objectContaining([
                {
                    name: name,
                    source: source,
                    title: title,
                    value: value
                }
            ])
        );
    });
});
