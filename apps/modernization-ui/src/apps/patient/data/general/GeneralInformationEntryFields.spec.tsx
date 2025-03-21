import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExtendedNewPatientEntry } from 'apps/patient/add/extended';
import { GeneralCodedValues } from './useGeneralCodedValues';
import { PatientProfilePermission } from 'apps/patient/profile/permission';
import { FormProvider, useForm } from 'react-hook-form';
import { GeneralInformationEntryFields } from './GeneralInformationEntryFields';

const mockPermissions: PatientProfilePermission = { delete: true, compareInvestigation: false, hivAccess: false };

jest.mock('apps/patient/profile/permission/usePatientProfilePermissions', () => ({
    usePatientProfilePermissions: () => mockPermissions
}));

const mockGeneralCodedValues: GeneralCodedValues = {
    maritalStatuses: [{ name: 'Married', value: 'M' }],
    educationLevels: [{ name: '1 or more years of college', value: '1' }],
    primaryOccupations: [{ name: 'Tester', value: 'T' }],
    primaryLanguages: [{ name: 'Welsh', value: 'W' }],
    speaksEnglish: [{ name: 'Yes', value: 'Y' }]
};

jest.mock('./useGeneralCodedValues', () => ({
    useGeneralCodedValues: () => mockGeneralCodedValues
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
describe('when entering patient general information demographics', () => {
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
        const { getByLabelText, getByText } = render(<Fixture />);

        const dateInput = getByLabelText('General information as of');

        const user = userEvent.setup();

        await user.click(dateInput).then(() => user.tab());

        expect(getByText('The General information as of is required.')).toBeInTheDocument();
    });
});
