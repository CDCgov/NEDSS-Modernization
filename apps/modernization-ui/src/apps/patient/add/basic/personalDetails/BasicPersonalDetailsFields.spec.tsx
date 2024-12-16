import { act, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { PatientSexBirthCodedValue } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicPersonalDetailsFields } from './BasicPersonalDetailsFields';
import { PatientProfilePermission } from 'apps/patient/profile/permission';
import { PatientGeneralCodedValue } from 'apps/patient/profile/generalInfo';
import { BasicPersonalDetailsEntry } from '../entry';

const mockPermissions: PatientProfilePermission = { delete: true, compareInvestigation: false, hivAccess: true };

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

const Fixture = () => {
    const form = useForm<BasicPersonalDetailsEntry>({
        mode: 'onBlur'
    });

    return (
        <FormProvider {...form}>
            <BasicPersonalDetailsFields />
        </FormProvider>
    );
};

describe('when entering patient sex and birth demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        expect(getByLabelText('Date of birth')).toBeInTheDocument();
        expect(getByText('Current age')).toBeInTheDocument();
        expect(getByLabelText('Current sex')).toBeInTheDocument();
        expect(getByLabelText('Birth sex')).toBeInTheDocument();
        expect(getByLabelText('Is the patient deceased?')).toBeInTheDocument();
        expect(getByLabelText('Marital status')).toBeInTheDocument();
        expect(getByLabelText('State HIV case ID')).toBeInTheDocument();
    });

    it('should validate date of birth', async () => {
        const { getByLabelText, findByText, queryByText } = render(<Fixture />);
        const dateOfBirth = getByLabelText('Date of birth');

        expect(queryByText('The Date of birth should be in the format MM/DD/YYYY.')).not.toBeInTheDocument();

        act(() => {
            userEvent.clear(dateOfBirth);
            userEvent.type(dateOfBirth, '123');
            userEvent.tab();
        });

        expect(await findByText('The Date of birth should be in the format MM/DD/YYYY.')).toBeInTheDocument();
    });

    it('should not render age when date of birth not set', async () => {
        const { container, getByText } = render(<Fixture />);
        expect(getByText('Current age')).toBeInTheDocument();
        expect(container.querySelector('span.value')?.innerHTML).toBe('');
    });

    it('should enable Date of death when deceased is true', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const deceased = getByLabelText('Is the patient deceased?');
        expect(queryByLabelText('Date of death')).not.toBeInTheDocument();

        act(() => {
            userEvent.selectOptions(deceased, 'Y');
        });
        await waitFor(() => {
            expect(getByLabelText('Date of death')).toBeInTheDocument();
        });
    });

    it('should not render the HIV case ID when user does not have permission', async () => {
        mockPermissions.hivAccess = false;
        const { queryByLabelText } = render(<Fixture />);
        expect(queryByLabelText('State HIV case ID')).not.toBeInTheDocument();
    });
});
