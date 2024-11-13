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
            lastName: { equals: 'last-name-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'lastName', title: 'LAST NAME', name: 'last-name-value', value: 'last-name-value' }
            ])
        );
    });

    it('should resolve terms with first name', () => {
        const input: PatientCriteriaEntry = {
            firstName: { equals: 'first-name-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'firstName', title: 'FIRST NAME', name: 'first-name-value', value: 'first-name-value' }
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
            expect.arrayContaining([{ source: 'id', title: 'PATIENT ID', name: 'id-value', value: 'id-value' }])
        );
    });
});

describe('when the PatientCriteria contains Address criteria', () => {
    it('should resolve terms with Street address', () => {
        const input: PatientCriteriaEntry = {
            address: { equals: 'address-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'address', title: 'STREET ADDRESS', name: 'address-value', value: 'address-value' }
            ])
        );
    });

    it('should resolve terms with City', () => {
        const input: PatientCriteriaEntry = {
            city: 'city-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'city', title: 'CITY', name: 'city-value', value: 'city-value' }])
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
