import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExtendedNewPatientEntry } from 'apps/patient/add/extended';
import { FormProvider, useForm } from 'react-hook-form';
import { SexAndBirthEntryFields } from './SexAndBirthEntryFields';
import { SexBirthCodedValues } from './useSexBirthCodedValues';

const mockNow = jest.fn();

jest.mock('design-system/date/clock', () => ({
    now: () => mockNow()
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

jest.mock('./useSexBirthCodedValues', () => ({
    useSexBirthCodedValues: () => mockSexBirthCodedValues
}));

const mockLocationOptions = {
    states: [{ name: 'StateName', value: '1' }],
    counties: [{ name: 'CountyName', value: '2' }],
    countries: [{ name: 'CountryName', value: '3' }],
    state: jest.fn()
};

jest.mock('options/location', () => ({
    useLocationOptions: () => mockLocationOptions
}));

const Fixture = ({ formValues }: { formValues?: Partial<ExtendedNewPatientEntry> }) => {
    const form = useForm<ExtendedNewPatientEntry>({
        mode: 'onBlur',
        defaultValues: { birthAndSex: { multiple: { value: 'Y', name: 'Yes' } }, ...formValues }
    });

    return (
        <FormProvider {...form}>
            <SexAndBirthEntryFields />
        </FormProvider>
    );
};

describe('when entering patient sex and birth demographics', () => {
    beforeEach(() => {
        mockNow.mockReturnValue(new Date('2022-01-25T00:00:00'));
    });

    it('should render the proper labels', () => {
        const { getByLabelText, getByText, queryByLabelText } = render(<Fixture />);

        expect(getByLabelText('Sex & birth information as of')).toBeInTheDocument();
        expect(getByLabelText('Date of birth')).toBeInTheDocument();
        expect(getByText('Current age')).toBeInTheDocument();
        expect(getByLabelText('Current sex')).toBeInTheDocument();
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();
        expect(getByLabelText('Transgender information')).toBeInTheDocument();
        expect(getByLabelText('Additional gender')).toBeInTheDocument();
        expect(getByLabelText('Birth sex')).toBeInTheDocument();
        expect(getByLabelText('Multiple birth')).toBeInTheDocument();
        expect(getByLabelText('Birth order')).toBeInTheDocument();
        expect(getByLabelText('Birth city')).toBeInTheDocument();
        expect(getByLabelText('Birth state')).toBeInTheDocument();
        expect(getByLabelText('Birth county')).toBeInTheDocument();
        expect(getByLabelText('Birth country')).toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Sex & birth information as of');

        await user.click(asOf);
        await user.tab();

        expect(await findByText('The Sex & birth information as of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Sex & birth information as of');

        await user.type(asOf, '01/20/2020');
        await user.tab();

        expect(queryByText('The Sex & birth information as of is required.')).not.toBeInTheDocument();
    });

    it('should enable unknown reason when current sex is unknown', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const currentSex = getByLabelText('Current sex');
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();

        await user.selectOptions(currentSex, 'U');
        expect(getByLabelText('Unknown reason')).not.toBeDisabled();
    });

    it('should reset unknown reason when current sex is changed from unknown', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const currentSex = getByLabelText('Current sex');
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();

        await user.selectOptions(currentSex, 'U');
        expect(getByLabelText('Unknown reason')).not.toBeDisabled();

        await user.selectOptions(getByLabelText('Unknown reason'), 'DNA');
        expect(getByLabelText('Unknown reason')).toHaveValue('DNA');
    });

    it('should render age only when date of birth is valid', async () => {
        const user = userEvent.setup();

        const { queryByText, getByText, getByLabelText } = render(<Fixture />);
        const dateOfBirth = getByLabelText('Date of birth');

        expect(getByText('Current age')).toBeInTheDocument();
        expect(queryByText(/year|month|day/)).not.toBeInTheDocument();

        await user.clear(dateOfBirth).then(() => user.type(dateOfBirth, '12012012{tab}'));

        expect(getByText('9 years')).toBeInTheDocument();
    });

    it('should calculate currentAge against the deceasedOn date when provided', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(
            <Fixture formValues={{ mortality: { deceasedOn: '12/12/2022', asOf: '05/05/2025' } }} />
        );
        const dateOfBirth = getByLabelText('Date of birth');

        await user.clear(dateOfBirth).then(() => user.type(dateOfBirth, '12012012{tab}'));

        expect(getByText('10 years')).toBeInTheDocument();
    });
    it('should have accessibility description for the as of date field', () => {
        const { getByLabelText } = render(<Fixture />);
        const dateInput = getByLabelText('Sex & birth information as of');
        expect(dateInput).toHaveAttribute(
            'aria-description',
            "This field defaults to today's date and can be changed if needed."
        );
    });
});
