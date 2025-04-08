import { asBasicNewPatientEntry } from './asBasicNewPatientEntry';

const defaults = { administrative: { asOf: '04/04/2004' } };

describe('when adding a new patient from a patient search', () => {
    it('should default the administrative as of date from the provided defaults', () => {
        const actual = asBasicNewPatientEntry({ administrative: { asOf: '03/10/1997' } })({});

        expect(actual).toEqual(
            expect.objectContaining({
                administrative: expect.objectContaining({ asOf: '03/10/1997' })
            })
        );
    });

    it('should populate the first name from the First name text criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            name: { first: { equals: 'first-name-value' } }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                name: expect.objectContaining({ first: 'first-name-value' })
            })
        );
    });

    it('should populate the last name from the Last name text criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            name: { last: { equals: 'last-name-value' } }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                name: expect.objectContaining({ last: 'last-name-value' })
            })
        );
    });

    it('should populate the date of birth from the date equals criteria with a full date', () => {
        const criteria = { bornOn: { equals: { month: 6, day: 5, year: 2025 } } };

        const actual = asBasicNewPatientEntry(defaults)(criteria);

        expect(actual).toEqual(
            expect.objectContaining({ personalDetails: expect.objectContaining({ bornOn: '06/05/2025' }) })
        );
    });

    it('should not populate the date of birth from the date equals criteria with only a month', () => {
        const criteria = { bornOn: { equals: { month: 6 } } };

        const actual = asBasicNewPatientEntry(defaults)(criteria);

        expect(actual.personalDetails?.bornOn).toBeUndefined();
    });

    it('should not populate the date of birth from the date equals criteria with only a day', () => {
        const criteria = { bornOn: { equals: { day: 5 } } };

        const actual = asBasicNewPatientEntry(defaults)(criteria);

        expect(actual.personalDetails?.bornOn).toBeUndefined();
    });

    it('should not populate the date of birth from the date equals criteria with only a year', () => {
        const criteria = { bornOn: { equals: { year: 2025 } } };

        const actual = asBasicNewPatientEntry(defaults)(criteria);

        expect(actual.personalDetails?.bornOn).toBeUndefined();
    });

    it('should populate the current sex from the gender criteria when the value is not "No value"', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            gender: { name: 'Value', value: 'value' }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                personalDetails: expect.objectContaining({
                    currentSex: { name: 'Value', value: 'value' }
                })
            })
        );
    });

    it('should not populate the current sex from the Gender criteria when the value is "No value"', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            gender: { name: 'No value', value: 'NO_VALUE' }
        });

        expect(actual.personalDetails?.currentSex).toBeUndefined();
    });

    it('should populate the address street from the Address text criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            location: { street: { equals: 'street-value' } }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({ address1: 'street-value' })
            })
        );
    });

    it('should populate the address city from the City text criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            location: { city: { equals: 'city-value' } }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({ city: 'city-value' })
            })
        );
    });

    it('should populate the address state from the State criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            state: { name: 'State', value: 'state-value' }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    state: expect.objectContaining({ name: 'State', value: 'state-value' })
                })
            })
        );
    });

    it('should populate the address zip code from the Zip criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            zip: 449
        });

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    zipcode: '449'
                })
            })
        );
    });

    it('should default the address country from the provided defaults', () => {
        const actual = asBasicNewPatientEntry({
            administrative: { asOf: '03/10/1997' },
            address: { country: { name: 'Default country', value: 'default-country' } }
        })({});

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    country: expect.objectContaining({ name: 'Default country', value: 'default-country' })
                })
            })
        );
    });

    it('should populate the Phone & email home from the Phone number criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            phoneNumber: 'phone-number-value'
        });

        expect(actual).toEqual(
            expect.objectContaining({
                phoneEmail: expect.objectContaining({
                    home: 'phone-number-value'
                })
            })
        );
    });

    it('should populate the Phone & email email address from the Email address criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            email: 'email-value'
        });

        expect(actual).toEqual(
            expect.objectContaining({
                phoneEmail: expect.objectContaining({
                    email: 'email-value'
                })
            })
        );
    });

    it('should populate the ethnicity from the Ethnicity criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            ethnicity: { name: 'Ethnicity', value: 'ethnicity-value' }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                ethnicityRace: expect.objectContaining({
                    ethnicity: expect.objectContaining({ name: 'Ethnicity', value: 'ethnicity-value' })
                })
            })
        );
    });

    it('should populate races from the Race criteria', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            race: { name: 'Race', value: 'race-value' }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                ethnicityRace: expect.objectContaining({
                    races: expect.arrayContaining([expect.objectContaining({ name: 'Race', value: 'race-value' })])
                })
            })
        );
    });

    it('should populate identifications from the ID type and ID number', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            identificationType: { name: 'Identification type', value: 'identification-type-value' },
            identification: 'identification-value'
        });

        expect(actual).toEqual(
            expect.objectContaining({
                identifications: expect.arrayContaining([
                    expect.objectContaining({
                        id: 'identification-value',
                        type: expect.objectContaining({
                            name: 'Identification type',
                            value: 'identification-type-value'
                        })
                    })
                ])
            })
        );
    });

    it('should not populate identifications without an ID number', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            identificationType: { name: 'Identification type', value: 'identification-type-value' }
        });

        expect(actual).toEqual(
            expect.objectContaining({
                identifications: []
            })
        );
    });

    it('should not populate identifications without an ID type', () => {
        const actual = asBasicNewPatientEntry(defaults)({
            identification: 'identification-value'
        });

        expect(actual).toEqual(
            expect.objectContaining({
                identifications: []
            })
        );
    });
});
