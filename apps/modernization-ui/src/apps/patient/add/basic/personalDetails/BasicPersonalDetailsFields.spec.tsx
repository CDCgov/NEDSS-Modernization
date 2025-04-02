import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SexBirthCodedValues } from 'apps/patient/data/sexAndBirth/useSexBirthCodedValues';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicPersonalDetailsFields } from './BasicPersonalDetailsFields';
import { PatientProfilePermission } from 'apps/patient/profile/permission';
import { BasicPersonalDetailsEntry } from '../entry';
import { GeneralCodedValues } from 'apps/patient/data/general/useGeneralCodedValues';

const mockNow = jest.fn();

jest.mock('design-system/date/clock', () => ({
    now: () => mockNow()
}));

const mockPermissions: PatientProfilePermission = { delete: true, compareInvestigation: false, hivAccess: true };

jest.mock('apps/patient/profile/permission/usePatientProfilePermissions', () => ({
    usePatientProfilePermissions: () => mockPermissions
}));

const mockPatientCodedValues: GeneralCodedValues = {
    maritalStatuses: [{ name: 'Married', value: 'M' }],
    primaryOccupations: [{ name: 'Tester', value: 'T' }],
    educationLevels: [{ name: '1 or more years of college', value: '1' }],
    primaryLanguages: [{ name: 'Welsh', value: 'W' }],
    speaksEnglish: [{ name: 'Yes', value: 'Y' }]
};

jest.mock('apps/patient/data/general/useGeneralCodedValues', () => ({
    useGeneralCodedValues: () => mockPatientCodedValues
}));

const mockSexBirthCodedValues: SexBirthCodedValues = {
    genders: [
        { name: 'Male', value: 'M' },
        { name: 'Female', value: 'F' },
        { name: 'Unknown', value: 'U' }
    ],
    preferredGenders: [{ name: 'FTM', value: 'FTM' }],
    genderUnknownReasons: [{ name: 'Did not ask', value: 'DNA' }],
    multipleBirth: [{ name: 'Yes', value: 'Y' }]
};

jest.mock('apps/patient/data/sexAndBirth/useSexBirthCodedValues', () => ({
    useSexBirthCodedValues: () => mockSexBirthCodedValues
}));

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
    const form = useForm<BasicPersonalDetailsEntry>({
        mode: 'onBlur'
    });

    return (
        <FormProvider {...form}>
            <BasicPersonalDetailsFields sizing={props.sizing} />
        </FormProvider>
    );
};

describe('when entering patient sex and birth demographics', () => {
    beforeEach(() => {
        mockNow.mockReturnValue(new Date('2020-01-25T00:00:00'));
    });

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

    it('should render the proper labels with the small size when sizing is set to small', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        expect(getByText('Date of birth').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Current sex').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Birth sex').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Is the patient deceased?').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Marital status').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('State HIV case ID').parentElement?.parentElement).toHaveClass('small');
    });

    it('should validate date of birth', async () => {
        const { getByLabelText, getByText, queryByText } = render(<Fixture />);
        const dateOfBirth = getByLabelText('Date of birth');

        expect(queryByText('The Date of birth should be in the format MM/DD/YYYY.')).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.clear(dateOfBirth).then(() => user.type(dateOfBirth, '123{tab}'));

        expect(getByText('The Date of birth should be in the format MM/DD/YYYY.')).toBeInTheDocument();
    });

    it('should not render age when date of birth not set', async () => {
        const { queryByText, getByText } = render(<Fixture />);
        expect(getByText('Current age')).toBeInTheDocument();
        expect(queryByText(/year|month|day/)).not.toBeInTheDocument();
    });

    it('should render age only when date of birth is valid', async () => {
        const { queryByText, getByText, getByLabelText } = render(<Fixture />);
        const dateOfBirth = getByLabelText('Date of birth');

        expect(getByText('Current age')).toBeInTheDocument();
        expect(queryByText(/year|month|day/)).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.clear(dateOfBirth).then(() => user.type(dateOfBirth, '12012012{tab}'));

        expect(getByText('7 years')).toBeInTheDocument();
    });

    it('should enable Date of death when deceased is true', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const deceased = getByLabelText('Is the patient deceased?');
        expect(queryByLabelText('Date of death')).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.selectOptions(deceased, 'Y');

        expect(getByLabelText('Date of death')).toBeInTheDocument();
    });

    it('should not render the HIV case ID when user does not have permission', async () => {
        mockPermissions.hivAccess = false;
        const { queryByLabelText } = render(<Fixture />);
        expect(queryByLabelText('State HIV case ID')).not.toBeInTheDocument();
    });
});
