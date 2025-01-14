import { asNewPatientEntry } from './asNewPatientEntry';

describe('when adding a new patient from a patient search', () => {
    it('should populate the first name that was searched for', () => {
        const criteria = { name: { first: { contains: 'first-name' } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ firstName: 'first-name' }));
    });

    it('should populate the last name that was searched for', () => {
        const criteria = { name: { last: { equals: 'last-name' } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ lastName: 'last-name' }));
    });

    it('should populate the date of birth from the date equals criteria with a full date', () => {
        const criteria = { bornOn: { equals: { month: 6, day: 5, year: 2025 } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ dateOfBirth: '06/05/2025' }));
    });

    it('should populate the date of birth from the date equals criteria with only a month', () => {
        const criteria = { bornOn: { equals: { month: 6 } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual.dateOfBirth).toBeUndefined();
    });

    it('should populate the date of birth from the date equals criteria with only a day', () => {
        const criteria = { bornOn: { equals: { day: 5 } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual.dateOfBirth).toBeUndefined();
    });

    it('should populate the date of birth from the date equals criteria with only a year', () => {
        const criteria = { bornOn: { equals: { year: 2025 } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual.dateOfBirth).toBeUndefined();
    });

    it('should populate the gender that was searched for', () => {
        const criteria = { gender: { name: 'Female', label: 'Female', value: 'F' } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ currentGender: 'F' }));
    });

    it('should populate the street address that was searched for', () => {
        const criteria = { location: { street: { equals: 'address-value' } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ streetAddress1: 'address-value' }));
    });

    it('should populate the city that was searched for', () => {
        const criteria = { location: { city: { equals: 'city-value' } } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ city: 'city-value' }));
    });

    it('should populate the state that was searched for', () => {
        const criteria = { state: { name: 'State', label: 'State', value: 'state-value' } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ state: expect.objectContaining({ value: 'state-value' }) }));
    });

    it('should populate the zip that was searched for', () => {
        const criteria = { zip: 1013 };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ zip: '1013' }));
    });

    it('should populate the phone number that was searched for as the home phone', () => {
        const criteria = { phoneNumber: 'phone-number-value' };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ homePhone: 'phone-number-value' }));
    });

    it('should populate the email address that was searched for', () => {
        const criteria = { email: 'email-value' };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(
            expect.objectContaining({
                emailAddresses: expect.arrayContaining([expect.objectContaining({ email: 'email-value' })])
            })
        );
    });

    it('should default the email address when it was not searched for', () => {
        const criteria = {};

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(
            expect.objectContaining({
                emailAddresses: expect.arrayContaining([expect.objectContaining({ email: '' })])
            })
        );
    });

    it('should populate the ethnicity that was searched for', () => {
        const criteria = { ethnicity: { name: 'Ethnicity', label: 'Ethnicity', value: 'ethnicity-value' } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ ethnicity: 'ethnicity-value' }));
    });

    it('should populate the race that was searched for', () => {
        const criteria = { race: { name: 'Race', label: 'Race', value: 'race-value' } };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(expect.objectContaining({ race: ['race-value'] }));
    });

    it('should populate the identification that was searched for', () => {
        const criteria = {
            identificationType: {
                name: 'Identification Type',
                label: 'Identification Type',
                value: 'identification-type-value'
            },
            identification: 'identification-value'
        };

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(
            expect.objectContaining({
                identification: expect.arrayContaining([
                    expect.objectContaining({
                        type: 'identification-type-value',
                        value: 'identification-value',
                        authority: null
                    })
                ])
            })
        );
    });

    it('should default the identification when it was not searched for', () => {
        const criteria = {};

        const actual = asNewPatientEntry(criteria);

        expect(actual).toEqual(
            expect.objectContaining({
                identification: expect.arrayContaining([
                    expect.objectContaining({
                        type: null,
                        value: null,
                        authority: null
                    })
                ])
            })
        );
    });
});
