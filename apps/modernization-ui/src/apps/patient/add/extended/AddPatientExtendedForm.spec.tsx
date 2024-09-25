import { render, screen } from '@testing-library/react';
import { PatientSexBirthCodedValue } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { CodedValue } from 'coded';
import { CountiesCodedValues } from 'location';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { PatientEthnicityCodedValue } from 'apps/patient/profile/ethnicity';
import { PatientProfilePermission } from 'apps/patient/profile/permission';
import { PatientGeneralCodedValue } from 'apps/patient/profile/generalInfo';
import { internalizeDate } from 'date';

const mockSexBirthCodedValues: PatientSexBirthCodedValue = {
    genders: [
        { name: 'Male', value: 'M' },
        { name: 'Female', value: 'F' },
        { name: 'Unknown', value: 'U' }
    ],
    preferredGenders: [{ name: 'FTM', value: 'FTM' }],
    genderUnknownReasons: [{ name: 'Did not ask', value: 'DNA' }],
    multipleBirth: [{ name: 'Yes', value: 'Y' }],
    states: {
        all: [{ name: 'Alabama', value: 'AL', abbreviation: 'AL' }],
        byValue: jest.fn(),
        byAbbreviation: jest.fn()
    },
    counties: {
        byState: jest.fn()
    },
    countries: [{ name: 'United States of America', value: 'US' }]
};

jest.mock('apps/patient/profile/sexBirth/usePatientSexBirthCodedValues', () => ({
    usePatientSexBirthCodedValues: () => mockSexBirthCodedValues
}));

const mockCountyCodedValues: CountiesCodedValues = { counties: [{ name: 'CountyA', value: 'A', group: 'G' }] };
jest.mock('location/useCountyCodedValues', () => ({
    useCountyCodedValues: () => mockCountyCodedValues
}));
const mockPatientAddressCodedValues = {
    types: [{ name: 'House', value: 'H' }],
    uses: [{ name: 'Home', value: 'HM' }]
};

jest.mock('apps/patient/profile/addresses/usePatientAddressCodedValues', () => ({
    usePatientAddressCodedValues: () => mockPatientAddressCodedValues
}));

const mockLocationCodedValues = {
    states: {
        all: [{ name: 'StateName', value: '1' }]
    },
    counties: {
        byState: (state: string) => [{ name: 'CountyName', value: '2' }]
    },
    countries: [{ name: 'CountryName', value: '3' }]
};

jest.mock('location/useLocationCodedValues', () => ({
    useLocationCodedValues: () => mockLocationCodedValues
}));
const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/profile/phoneEmail/usePatientPhoneCodedValues', () => ({
    usePatientPhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const mockRaceCodedValues: CodedValue[] = [{ value: '1', name: 'race name' }];

jest.mock('coded/race/useRaceCodedValues', () => ({
    useRaceCodedValues: () => mockRaceCodedValues
}));

const mockDetailedOptions: CodedValue[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('coded/race/useDetailedRaceCodedValues', () => ({
    useDetailedRaceCodedValues: () => mockDetailedOptions
}));

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};
jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

const mockPatientIdentificationCodedValues: PatientIdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

jest.mock('apps/patient/profile/identification/usePatientIdentificationCodedValues', () => ({
    usePatientIdentificationCodedValues: () => mockPatientIdentificationCodedValues
}));

const mockEthnicityValues: PatientEthnicityCodedValue = {
    ethnicGroups: [
        { name: 'Hispanic or Latino', value: '2135-2' },
        { name: 'Unknown', value: 'UNK' }
    ],
    ethnicityUnknownReasons: [{ name: 'Not asked', value: '6' }],
    detailedEthnicities: [{ name: 'Central American', value: '2155-0' }]
};

jest.mock('apps/patient/profile/ethnicity/usePatientEthnicityCodedValues', () => ({
    usePatientEthnicityCodedValues: () => mockEthnicityValues
}));

const mockPermissions: PatientProfilePermission = { delete: true, compareInvestigation: false, hivAccess: false };

jest.mock('apps/patient/profile/permission/usePatientProfilePermissions', () => ({
    usePatientProfilePermissions: () => mockPermissions
}));

const mockPatientCodedValues: PatientGeneralCodedValue = {
    maritalStatuses: [{ name: 'Married', value: 'M' }],
    primaryOccupations: [{ name: 'Tester', value: 'T' }],
    educationLevels: [{ name: '1 or more years of college', value: '1' }],
    primaryLanguages: [{ name: 'Welsh', value: 'W' }],
    speaksEnglish: [{ name: 'Yes', value: 'Y' }]
};

jest.mock('apps/patient/profile/generalInfo/usePatientGeneralCodedValues', () => ({
    usePatientGeneralCodedValues: () => mockPatientCodedValues
}));

const awaitRender = async () => {
    // wait on render to prevent act warning
    expect(await screen.findByText('Administrative')).toBeInTheDocument();
};

const Fixture = () => {
    return <AddPatientExtendedForm />;
};
describe('AddPatientExtendedForm', () => {
    it('should render the sections with appropriate help text', async () => {
        const { getByText, getAllByRole } = render(<Fixture />);
        await awaitRender();
        const headers = getAllByRole('heading');
        expect(headers[0]).toHaveTextContent('Administrative');
        expect(headers[0].parentElement).toContainElement(getByText('All required fields for adding comments'));

        expect(headers[1]).toHaveTextContent('Name');
        expect(headers[1].parentElement).toContainElement(getByText('All required fields for adding name'));

        expect(headers[2]).toHaveTextContent('Address');
        expect(headers[2].parentElement).toContainElement(getByText('All required fields for adding address'));

        expect(headers[3]).toHaveTextContent('Phone & email');
        expect(headers[3].parentElement).toContainElement(getByText('All required fields for adding phone & email'));

        expect(headers[4]).toHaveTextContent('Identification');
        expect(headers[4].parentElement).toContainElement(getByText('All required fields for adding identification'));

        expect(headers[5]).toHaveTextContent('Race');
        expect(headers[5].parentElement).toContainElement(getByText('All required fields for adding race'));

        expect(headers[6]).toHaveTextContent('Ethnicity');
        expect(headers[6].parentElement).toContainElement(getByText('All required fields for adding ethnicity'));

        expect(headers[7]).toHaveTextContent('Sex & birth');
        expect(headers[7].parentElement).toContainElement(getByText('All required fields for adding sex & birth'));

        expect(headers[8]).toHaveTextContent('Mortality');
        expect(headers[8].parentElement).toContainElement(getByText('All required fields for adding mortality'));

        expect(headers[9]).toHaveTextContent('General patient information');
        expect(headers[9].parentElement).toContainElement(
            getByText('All required fields for adding general patient information')
        );
    });

    it('should set today as default date for as of fields', async () => {
        const { getByLabelText } = render(<Fixture />);
        await awaitRender();
        const expected = internalizeDate(new Date());
        expect(getByLabelText('Information as of date')).toHaveValue(expected);

        expect(getByLabelText('Name as of')).toHaveValue(expected);

        expect(getByLabelText('Address as of')).toHaveValue(expected);

        expect(getByLabelText('Phone & email as of')).toHaveValue(expected);

        expect(getByLabelText('Identification as of')).toHaveValue(expected);

        expect(getByLabelText('Race as of')).toHaveValue(expected);

        expect(getByLabelText('Ethnicity information as of')).toHaveValue(expected);

        expect(getByLabelText('Sex & birth information as of')).toHaveValue(expected);

        expect(getByLabelText('Mortality information as of')).toHaveValue(expected);

        expect(getByLabelText('General information as of')).toHaveValue(expected);
    });
});
