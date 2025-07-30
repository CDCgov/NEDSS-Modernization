import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SexBirthCodedValues } from 'apps/patient/data/sexAndBirth/useSexBirthCodedValues';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicPersonalDetailsFields } from './BasicPersonalDetailsFields';
import { BasicPersonalDetailsEntry } from '../entry';
import { GeneralCodedValues } from 'apps/patient/data/general/useGeneralCodedValues';

const mockNow = jest.fn();

let mockPermissions: string[] = [];

jest.mock('user', () => ({
    useUser: () => ({ state: { user: { permissions: mockPermissions } } })
}));

jest.mock('design-system/date/clock', () => ({
    now: () => mockNow()
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
        const { getByLabelText, getByText, getByRole } = render(<Fixture />);

        expect(getByRole('textbox', { name: /Date of birth/i })).toBeInTheDocument();
        expect(getByText('Current age')).toBeInTheDocument();
        expect(getByLabelText('Current sex')).toBeInTheDocument();
        expect(getByLabelText('Birth sex')).toBeInTheDocument();
        expect(getByLabelText('Is the patient deceased?')).toBeInTheDocument();
        expect(getByLabelText('Marital status')).toBeInTheDocument();
    });

    it('should render the proper labels with the small size when sizing is set to small', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        expect(getByText('Date of birth').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Current sex').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Birth sex').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Is the patient deceased?').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Marital status').parentElement?.parentElement).toHaveClass('small');
    });

    it('should validate date of birth', async () => {
        const { getByText, queryByText, getByRole } = render(<Fixture />);
        const dateOfBirth = getByRole('textbox', { name: /Date of birth/i });

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
        const { queryByText, getByText, getByRole } = render(<Fixture />);
        const dateOfBirth = getByRole('textbox', { name: /Date of birth/i });

        expect(getByText('Current age')).toBeInTheDocument();
        expect(queryByText(/year|month|day/)).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.clear(dateOfBirth).then(() => user.type(dateOfBirth, '12012012{tab}'));

        expect(getByText('7 years')).toBeInTheDocument();
    });

    it('should enable Date of death when deceased is true', async () => {
        const { getByLabelText, queryByLabelText, getByRole } = render(<Fixture />);

        const deceased = getByLabelText('Is the patient deceased?');
        expect(queryByLabelText('Date of death')).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.selectOptions(deceased, 'Y');

        expect(getByRole('textbox', { name: /Date of death/i })).toBeInTheDocument();
    });

    it('should not enable Date of death when deceased is false', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const deceased = getByLabelText('Is the patient deceased?');
        expect(queryByLabelText('Date of death')).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.selectOptions(deceased, 'N');

        expect(queryByLabelText('Date of death')).not.toBeInTheDocument();
    });

    it('should calculate currentAge against the deceasedOn date when provided', async () => {
        const { getByLabelText, getByText, getByRole } = render(<Fixture />);
        const dateOfBirth = getByRole('textbox', { name: /Date of birth/i });
        const deceased = getByLabelText('Is the patient deceased?');

        const user = userEvent.setup();
        await user.selectOptions(deceased, 'Y');

        const deceasedOn = getByRole('textbox', { name: /Date of death/i });
        await user.clear(dateOfBirth).then(() => user.type(dateOfBirth, '12012012{tab}'));
        await user.clear(deceasedOn).then(() => user.type(deceasedOn, '01012018{tab}'));

        expect(getByText('5 years')).toBeInTheDocument();
    });

    it('should not render the HIV case ID when user does not have permission', async () => {
        const { queryByLabelText } = render(<Fixture />);
        expect(queryByLabelText('State HIV case ID')).not.toBeInTheDocument();
    });

    it('should render the HIV case ID when user does has the correct permission', async () => {
        mockPermissions = ['HIVQUESTIONS-GLOBAL'];
        const { getByLabelText } = render(<Fixture />);
        expect(getByLabelText('State HIV case ID')).toBeInTheDocument();
    });
});
