import { today } from 'date';
import { NewPatientEntry } from '../NewPatientEntry';
import { PatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { LocationCodedValues, StateCodedValue } from 'location';
import { CodedValue } from 'coded';
import { Selectable } from 'options';
import { act, renderHook } from '@testing-library/react-hooks';
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

        const { result } = renderHook(() => asExtendedNewPatientEntry(initial, nameCodes, addressCodes, raceCode));

        expect(result.current.names).toBeTruthy();
        expect(result.current.names?.length).toBe(1);
        expect(result.current.names?.at(0)?.first).toBe('test first');
        expect(result.current.names?.at(0)?.last).toBe('test last');
        expect(result.current.names?.at(0)?.middle).toBe('test middle');
        expect(result.current.names?.at(0)?.suffix?.label).toBe('Junior');
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

        const { result } = renderHook(() => asExtendedNewPatientEntry(initial, nameCodes, addressCodes, raceCode));

        expect(result.current.phoneEmails).toBeTruthy();
        expect(result.current.phoneEmails?.at(0)?.phoneNumber).toBe('1234567890');
        expect(result.current.phoneEmails?.at(0)?.type.label).toBe('Phone');
        expect(result.current.phoneEmails?.at(0)?.use.label).toBe('Home');

        expect(result.current.phoneEmails?.at(1)?.phoneNumber).toBe('0987654321');
        expect(result.current.phoneEmails?.at(1)?.type.label).toBe('Cellular phone');
        expect(result.current.phoneEmails?.at(1)?.use.label).toBe('Mobile contact');

        expect(result.current.phoneEmails?.at(2)?.phoneNumber).toBe('1231231234');
        expect(result.current.phoneEmails?.at(2)?.type.label).toBe('Phone');
        expect(result.current.phoneEmails?.at(2)?.use.label).toBe('Primary work place');

        expect(result.current.phoneEmails?.at(3)?.email).toBe('test@test.com');
    });

    it('identification basic to extended', () => {
        const date = today();
        const initial: NewPatientEntry = {
            asOf: date,
            identification: [{ type: 'ID', authority: 'test authority', value: '12344' }],
            phoneNumbers: [],
            emailAddresses: []
        };

        const { result } = renderHook(() => asExtendedNewPatientEntry(initial, nameCodes, addressCodes, raceCode));

        expect(result.current.identifications).toBeTruthy();
        expect(result.current.identifications?.at(0)?.id).toBe('12344');
        expect(result.current.identifications?.at(0)?.issuer?.name).toBe('test authority');
        expect(result.current.identifications?.at(0)?.type?.name).toBe('ID');
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

        const { result } = renderHook(() => asExtendedNewPatientEntry(initial, nameCodes, addressCodes, raceCode));

        expect(result.current.races).toBeTruthy();
        expect(result.current.races?.at(0)?.race.name).toBe('Asian');
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

        const { result } = renderHook(() => asExtendedNewPatientEntry(initial, nameCodes, addressCodes, raceCode));

        expect(result.current.ethnicity).toBeTruthy();
        expect(result.current.ethnicity?.ethnicity.name).toBe('Unknown');
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
            country: 'USA',
            state: 'AR',
            zip: '12345'
        };

        const { result } = renderHook(() => asExtendedNewPatientEntry(initial, nameCodes, addressCodes, raceCode));

        expect(result.current.addresses).toBeTruthy();
        expect(result.current.addresses?.at(0)?.address1).toBe('st address 1');
        expect(result.current.addresses?.at(0)?.address2).toBe('st address 2');
        expect(result.current.addresses?.at(0)?.country?.name).toBe('USA');
        expect(result.current.addresses?.at(0)?.state?.name).toBe('Arkansas');
        expect(result.current.addresses?.at(0)?.zipcode).toBe('12345');
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

        const { result } = renderHook(() => asExtendedNewPatientEntry(initial, nameCodes, addressCodes, raceCode));

        expect(result.current.birthAndSex?.bornOn).toBe('01/01/1999');
        expect(result.current.birthAndSex?.sex?.name).toBe('M');
        expect(result.current.general?.maritalStatus?.name).toBe('Anulled');
        expect(result.current.birthAndSex?.current?.name).toBe('M');
        expect(result.current.mortality?.deceased?.name).toBe('Y');
        expect(result.current.mortality?.deceasedOn).toBe('01/01/2004');
        expect(result.current.general?.stateHIVCase).toBe('1234');
        expect(result.current.administrative?.comment).toBe('test comments');
    });
});
