import { ExtendedNewPatientEntry } from 'apps/patient/add/extended';
import { MortalityEntryFields } from './MortalityEntryFields';
import { FormProvider, useForm } from 'react-hook-form';
import { internalizeDate } from 'date';
import { act, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

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

const Fixture = () => {
    const form = useForm<ExtendedNewPatientEntry>({
        mode: 'onBlur',
        defaultValues: { mortality: { asOf: internalizeDate(new Date()) } }
    });

    return (
        <FormProvider {...form}>
            <MortalityEntryFields />
        </FormProvider>
    );
};
describe('when entering patient mortality demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        expect(getByLabelText('Mortality information as of')).toBeInTheDocument();
        expect(getByLabelText('Is the patient deceased?')).toBeInTheDocument();
        expect(queryByLabelText('Date of death')).not.toBeInTheDocument();
        expect(queryByLabelText('Death city')).not.toBeInTheDocument();
        expect(queryByLabelText('Death state')).not.toBeInTheDocument();
        expect(queryByLabelText('Death county')).not.toBeInTheDocument();
        expect(queryByLabelText('Death country')).not.toBeInTheDocument();
    });

    it('should require as of', async () => {
        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Mortality information as of');
        act(() => {
            userEvent.clear(asOf);
            userEvent.tab();
        });

        expect(await findByText('The Mortality information as of is required.')).toBeInTheDocument();
    });

    it('should render the death information when deceased is true', () => {
        const { getByLabelText } = render(<Fixture />);

        const deceased = getByLabelText('Is the patient deceased?');

        userEvent.selectOptions(deceased, 'Y');

        expect(getByLabelText('Date of death')).toBeInTheDocument();
        expect(getByLabelText('Death city')).toBeInTheDocument();
        expect(getByLabelText('Death state')).toBeInTheDocument();
        expect(getByLabelText('Death county')).toBeInTheDocument();
        expect(getByLabelText('Death country')).toBeInTheDocument();
    });

    it('should reset the death information when deceased is reverted to false', () => {
        const { getByLabelText } = render(<Fixture />);
        // Set patient deceased Y
        userEvent.selectOptions(getByLabelText('Is the patient deceased?'), 'Y');

        // Enter data in previously hidden fields
        userEvent.paste(getByLabelText('Date of death'), '12/01/2020');
        userEvent.type(getByLabelText('Death city'), 'City value');
        userEvent.selectOptions(getByLabelText('Death state'), '1');
        userEvent.selectOptions(getByLabelText('Death county'), '2');
        userEvent.selectOptions(getByLabelText('Death country'), '3');

        // Toggle deceased to N then back to Y
        userEvent.selectOptions(getByLabelText('Is the patient deceased?'), 'N');
        userEvent.selectOptions(getByLabelText('Is the patient deceased?'), 'Y');

        // Verify previously input data is cleared
        expect(getByLabelText('Date of death')).toHaveValue('');
        expect(getByLabelText('Death city')).toHaveValue('');
        expect(getByLabelText('Death state')).toHaveValue('');
        expect(getByLabelText('Death county')).toHaveValue('');
        expect(getByLabelText('Death country')).toHaveValue('');
    });
});
