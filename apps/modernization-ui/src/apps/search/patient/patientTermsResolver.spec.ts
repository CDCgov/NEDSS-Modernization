import { PatientCriteriaEntry } from './criteria';
import { patientTermsResolver } from './patientTermsResovler';

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
            disableSoundex: true,
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual([]);
    });

    it('should resolve terms with last name', () => {
        const input: PatientCriteriaEntry = {
            lastName: 'last-name-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'lastName', name: 'LAST', value: 'last-name-value' }])
        );
    });

    it('should resolve terms with first name', () => {
        const input: PatientCriteriaEntry = {
            firstName: 'first-name-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'firstName', name: 'FIRST', value: 'first-name-value' }])
        );
    });

    it('should resolve terms with gender', () => {
        const input: PatientCriteriaEntry = {
            gender: { name: 'Female', label: 'Female', value: 'F' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(expect.arrayContaining([{ source: 'gender', name: 'SEX', value: 'Female' }]));
    });

    it('should resolve terms with patient id', () => {
        const input: PatientCriteriaEntry = {
            id: 'id-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(expect.arrayContaining([{ source: 'id', name: 'ID', value: 'id-value' }]));
    });
});

describe('when the PatientCriteria contains Address criteria', () => {
    it('should resolve terms with Street address', () => {
        const input: PatientCriteriaEntry = {
            address: 'address-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'address', name: 'ADDRESS', value: 'address-value' }])
        );
    });

    it('should resolve terms with City', () => {
        const input: PatientCriteriaEntry = {
            city: 'city-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(expect.arrayContaining([{ source: 'city', name: 'CITY', value: 'city-value' }]));
    });

    it('should resolve terms with State', () => {
        const input: PatientCriteriaEntry = {
            state: { name: 'State Name', label: 'State Label', value: 'state-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(expect.arrayContaining([{ source: 'state', name: 'STATE', value: 'State Name' }]));
    });

    it('should resolve terms with Zip', () => {
        const input: PatientCriteriaEntry = {
            zip: 1051,
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(expect.arrayContaining([{ source: 'zip', name: 'ZIP', value: '1051' }]));
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
            expect.arrayContaining([{ source: 'phoneNumber', name: 'PHONE', value: 'phone-number-value' }])
        );
    });

    it('should resolve terms with email', () => {
        const input: PatientCriteriaEntry = {
            email: 'email-value',
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(expect.arrayContaining([{ source: 'email', name: 'EMAIL', value: 'email-value' }]));
    });
});

describe('when the PatientCriteria contains Race / Ethnicity criteria', () => {
    it('should resolve terms with race', () => {
        const input: PatientCriteriaEntry = {
            race: { name: 'Race Name', label: 'Race Label', value: 'race-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(expect.arrayContaining([{ source: 'race', name: 'RACE', value: 'Race Name' }]));
    });

    it('should resolve terms with ethnicity', () => {
        const input: PatientCriteriaEntry = {
            ethnicity: { name: 'Ethnicity Name', label: 'Ethnicity Label', value: 'ethnicity-value' },
            status: []
        };

        const actual = patientTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([{ source: 'ethnicity', name: 'ETHNICITY', value: 'Ethnicity Name' }])
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
                { source: 'identificationType', name: 'ID TYPE', value: 'Identification Type Name' },
                { source: 'identification', name: 'ID', value: 'identification-value' }
            ])
        );
    });
});
