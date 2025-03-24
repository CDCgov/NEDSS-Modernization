import { act, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExtendedNewPatientEntry } from 'apps/patient/add/extended';
import { CountiesCodedValues } from 'location/useCountyCodedValues';
import { FormProvider, useForm } from 'react-hook-form';
import { SexAndBirthEntryFields } from './SexAndBirthEntryFields';
import { LocationCodedValues } from 'location';
import { SexBirthCodedValues } from './useSexBirthCodedValues';

const mockLocationCodedValues: LocationCodedValues = {
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

jest.mock('location/useLocationCodedValues', () => ({
    useLocationCodedValues: () => mockLocationCodedValues
}));

jest.mock('./useSexBirthCodedValues', () => ({
    useSexBirthCodedValues: () => mockSexBirthCodedValues
}));

const mockCountyCodedValues: CountiesCodedValues = { counties: [{ name: 'CountyA', value: 'A', group: 'G' }] };
jest.mock('location/useCountyCodedValues', () => ({
    useCountyCodedValues: () => mockCountyCodedValues
}));

const Fixture = () => {
    const form = useForm<ExtendedNewPatientEntry>({
        mode: 'onBlur',
        defaultValues: { birthAndSex: { multiple: { value: 'Y', name: 'Yes' } } }
    });

    return (
        <FormProvider {...form}>
            <SexAndBirthEntryFields />
        </FormProvider>
    );
};

describe('when entering patient sex and birth demographics', () => {
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
        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Sex & birth information as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });

        expect(await findByText('The Sex & birth information as of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Sex & birth information as of');

        act(() => {
            userEvent.paste(asOf, '01/20/2020');
            userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('The Sex & birth information as of is required.')).not.toBeInTheDocument();
        });
    });

    it('should enable unknown reason when current sex is unknown', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const currentSex = getByLabelText('Current sex');
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();

        userEvent.selectOptions(currentSex, 'U');
        expect(getByLabelText('Unknown reason')).not.toBeDisabled();
    });

    it('should reset unknown reason when current sex is changed from unknown', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const currentSex = getByLabelText('Current sex');
        expect(queryByLabelText('Unknown reason')).not.toBeInTheDocument();

        userEvent.selectOptions(currentSex, 'U');
        expect(getByLabelText('Unknown reason')).not.toBeDisabled();
        userEvent.selectOptions(getByLabelText('Unknown reason'), 'DNA');
        expect(getByLabelText('Unknown reason')).toHaveValue('DNA');
    });
});
