import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExtendedNewPatientEntry } from 'apps/patient/add/extended';
import { PatientGeneralCodedValue } from 'apps/patient/profile/generalInfo';
import { PatientProfilePermission } from 'apps/patient/profile/permission';
import { FormProvider, useForm } from 'react-hook-form';
import { GeneralInformationEntryFields } from './GeneralInformationEntryFields';
import { act } from 'react-dom/test-utils';

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

const Fixture = () => {
    const form = useForm<ExtendedNewPatientEntry>({
        mode: 'onBlur'
    });

    return (
        <FormProvider {...form}>
            <GeneralInformationEntryFields />
        </FormProvider>
    );
};
describe('GeneralInformationEntryFields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        expect(getByLabelText('General information as of')).toBeInTheDocument();
        expect(getByLabelText('Marital status')).toBeInTheDocument();
        expect(getByLabelText("Mother's maiden name")).toBeInTheDocument();
        expect(getByLabelText('Number of adults in residence')).toBeInTheDocument();
        expect(getByLabelText('Primary occupation')).toBeInTheDocument();
        expect(getByLabelText('Highest level of education')).toBeInTheDocument();
        expect(getByLabelText('Primary language')).toBeInTheDocument();
        expect(getByLabelText('Speaks English')).toBeInTheDocument();
        expect(queryByLabelText('State HIV case ID')).not.toBeInTheDocument();
    });

    it('should render the HIV case ID when user has permission', async () => {
        mockPermissions.hivAccess = true;
        const { getByLabelText } = render(<Fixture />);
        expect(getByLabelText('State HIV case ID')).toBeInTheDocument();
    });

    it('should require as of date', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const errorMessage = 'As of date is required.';
        const dateInput = getByLabelText('General information as of');

        expect(queryByText(errorMessage)).not.toBeInTheDocument();
        act(() => {
            userEvent.click(dateInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(queryByText(errorMessage)).toBeInTheDocument();
        });

        act(() => {
            userEvent.click(dateInput);
            userEvent.paste(dateInput, '12/01/2020');
            userEvent.tab();
        });
        await waitFor(() => {
            expect(queryByText(errorMessage)).not.toBeInTheDocument();
        });
    });
});