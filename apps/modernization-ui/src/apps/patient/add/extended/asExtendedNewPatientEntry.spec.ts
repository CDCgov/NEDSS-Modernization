import { today } from 'date';
import { NewPatientEntry } from '../NewPatientEntry';
import { PatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { LocationCodedValues } from 'location';
import { asSelectable, Selectable } from 'options';
import { asExtendedNewPatientEntry } from './asExtendedNewPatientEntry';
import { Deceased, Gender, Suffix } from 'generated/graphql/schema';

const nameCodes: PatientNameCodedValues = {
    types: [
        {
            value: 'T',
            name: 'Test'
        }
    ],
    prefixes: [{ value: 'SR', name: 'Senior' }],
    suffixes: [{ value: 'JR', name: 'Junior' }],
    degrees: []
};
const addressCodes: LocationCodedValues = {
    countries: [{ value: 'USA', name: 'United states america' }],
    states: {
        all: [{ value: 'AR', name: 'Arkansas', abbreviation: 'ARK' }],
        byAbbreviation: jest.fn(),
        byValue: jest.fn()
    },
    counties: {
        byState: jest.fn()
    }
};
const raceCode: Selectable[] = [{ value: 'A', name: 'Asian' }];

describe('Basic form to extended form data transfer', () => {
    it('basic name to extended', async () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            firstName: 'test first',
            identification: [],
            phoneNumbers: [],
            emailAddresses: [],
            lastName: 'test last',
            middleName: 'test middle',
            suffix: Suffix.Jr
        };

        const result = asExtendedNewPatientEntry(initial, nameCodes, raceCode);

        expect(result.names).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    first: 'test first',
                    last: 'test last',
                    middle: 'test middle',
                    suffix: expect.objectContaining({ value: 'JR' })
                })
            ])
        );
    });

    it('basic phone numbers to extended', async () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            identification: [],
            phoneNumbers: [],
            emailAddresses: [{ email: 'test@test.com' }],
            workPhone: '1231231234',
            homePhone: '1234567890',
            cellPhone: '0987654321'
        };

        const result = asExtendedNewPatientEntry(initial, nameCodes, raceCode);

        expect(result.phoneEmails).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    phoneNumber: '1234567890',
                    type: expect.objectContaining({ label: 'Phone' }),
                    use: expect.objectContaining({ label: 'Home' })
                }),
                expect.objectContaining({
                    phoneNumber: '0987654321',
                    type: expect.objectContaining({ label: 'Cellular phone' }),
                    use: expect.objectContaining({ label: 'Mobile contact' })
                }),
                expect.objectContaining({
                    phoneNumber: '1231231234',
                    type: expect.objectContaining({ label: 'Phone' }),
                    use: expect.objectContaining({ label: 'Primary work place' })
                }),
                expect.objectContaining({
                    email: 'test@test.com'
                })
            ])
        );
    });

    it('identification basic to extended', () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            identification: [{ type: 'ID', authority: 'test authority', value: '12344' }],
            phoneNumbers: [],
            emailAddresses: []
        };

        const result = asExtendedNewPatientEntry(initial, nameCodes, raceCode);

        expect(result.identifications).toBeTruthy();
        expect(result.identifications?.at(0)?.id).toBe('12344');
        expect(result.identifications?.at(0)?.issuer?.name).toBe('test authority');
        expect(result.identifications?.at(0)?.type?.name).toBe('ID');
    });
    it('race basic to extended', () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            identification: [],
            phoneNumbers: [],
            emailAddresses: [],
            race: ['A']
        };

        const result = asExtendedNewPatientEntry(initial, nameCodes, raceCode);

        expect(result.races).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    race: expect.objectContaining({
                        name: 'Asian'
                    })
                })
            ])
        );
    });
    it('ethnicity basic to extended', () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            identification: [],
            phoneNumbers: [],
            emailAddresses: [],
            ethnicity: 'Unknown'
        };

        const result = asExtendedNewPatientEntry(initial, nameCodes, raceCode);

        expect(result).toEqual(
            expect.objectContaining({
                ethnicity: expect.objectContaining({ ethnicGroup: expect.objectContaining({ value: 'Unknown' }) })
            })
        );
    });
    it('address basic to extended', () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            identification: [],
            phoneNumbers: [],
            emailAddresses: [],
            streetAddress1: 'st address 1',
            streetAddress2: 'st address 2',
            country: asSelectable('USA'),
            state: asSelectable('AR', 'Arkansas'),
            zip: '12345'
        };

        const result = asExtendedNewPatientEntry(initial, nameCodes, raceCode);

        expect(result.addresses).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    address1: 'st address 1',
                    address2: 'st address 2',
                    country: expect.objectContaining({
                        name: 'USA'
                    }),
                    state: expect.objectContaining({
                        name: 'Arkansas'
                    }),
                    zipcode: '12345'
                })
            ])
        );
    });
    it('other information basic to extended', () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            identification: [],
            phoneNumbers: [],
            emailAddresses: [],
            dateOfBirth: '01/01/1999',
            currentGender: Gender.M,
            birthGender: Gender.M,
            deceased: Deceased.Y,
            deceasedTime: '01/01/2004',
            maritalStatus: 'Anulled',
            stateHIVCase: '1234',
            comments: 'test comments'
        };

        const result = asExtendedNewPatientEntry(initial, nameCodes, raceCode);

        expect(result.administrative?.comment).toBe('test comments');

        expect(result.birthAndSex).toEqual(
            expect.objectContaining({
                bornOn: '01/01/1999',
                sex: expect.objectContaining({
                    name: 'M'
                }),
                current: expect.objectContaining({
                    name: 'M'
                })
            })
        );

        expect(result.mortality).toEqual(
            expect.objectContaining({
                deceased: expect.objectContaining({
                    name: 'Y'
                }),
                deceasedOn: '01/01/2004'
            })
        );

        expect(result.general).toEqual(
            expect.objectContaining({
                maritalStatus: expect.objectContaining({ name: 'Anulled' }),
                stateHIVCase: '1234'
            })
        );
    });
});
