import { asNewPatientEntry } from './asNewPatientEntry';

describe('when addind a new patient from a patient search', () => {
    it('should populate the first name that was searched for', () => {
        const critiera = { firstName: 'first-name' };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ firstName: 'first-name' }));
    });

    it('should populate the last name that was searched for', () => {
        const critiera = { lastName: 'last-name' };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ lastName: 'last-name' }));
    });

    it('should populate the date of birth that was searched for', () => {
        const critiera = { dateOfBirth: '2025-06-05T11:13:19.010Z' };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ dateOfBirth: '06/05/2025' }));
    });

    it('should populate the gender that was searched for', () => {
        const critiera = { gender: { name: 'Female', label: 'Female', value: 'F' } };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ currentGender: 'F' }));
    });

    it('should populate the street address that was searched for', () => {
        const critiera = { address: 'address-value' };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ streetAddress1: 'address-value' }));
    });

    it('should populate the city that was searched for', () => {
        const critiera = { city: 'city-value' };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ city: 'city-value' }));
    });

    it('should populate the state that was searched for', () => {
        const critiera = { state: { name: 'State', label: 'State', value: 'state-value' } };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ state: 'state-value' }));
    });

    it('should populate the zip that was searched for', () => {
        const critiera = { zip: 1013 };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ zip: '1013' }));
    });

    it('should populate the phone number that was searched for as the home phone', () => {
        const critiera = { phoneNumber: 'phone-number-value' };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ homePhone: 'phone-number-value' }));
    });

    it('should populate the email address that was searched for', () => {
        const critiera = { email: 'email-value' };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(
            expect.objectContaining({
                emailAddresses: expect.arrayContaining([expect.objectContaining({ email: 'email-value' })])
            })
        );
    });

    it('should default the email address when it was not searched for', () => {
        const critiera = {};

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(
            expect.objectContaining({
                emailAddresses: expect.arrayContaining([expect.objectContaining({ email: '' })])
            })
        );
    });

    it('should populate the ethnicity that was searched for', () => {
        const critiera = { ethnicity: { name: 'Ethnicity', label: 'Ethnicity', value: 'ethnicity-value' } };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ ethnicity: 'ethnicity-value' }));
    });

    it('should populate the race that was searched for', () => {
        const critiera = { race: { name: 'Race', label: 'Race', value: 'race-value' } };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(expect.objectContaining({ race: ['race-value'] }));
    });

    it('should populate the identification that was searched for', () => {
        const critiera = {
            identificationType: {
                name: 'Identification Type',
                label: 'Identification Type',
                value: 'identification-type-value'
            },
            identification: 'identifcation-value'
        };

        const actual = asNewPatientEntry(critiera);

        expect(actual).toEqual(
            expect.objectContaining({
                identification: expect.arrayContaining([
                    expect.objectContaining({
                        type: 'identification-type-value',
                        value: 'identifcation-value',
                        authority: null
                    })
                ])
            })
        );
    });

    it('should default the identification when it was not searched for', () => {
        const critiera = {};

        const actual = asNewPatientEntry(critiera);

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
