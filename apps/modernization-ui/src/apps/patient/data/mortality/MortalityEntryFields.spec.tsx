import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExtendedNewPatientEntry } from 'apps/patient/add/extended';
import { MortalityEntryFields } from './MortalityEntryFields';
import { internalizeDate } from 'date';

const mockLocationOptions = {
    states: [{ name: 'StateName', value: '1' }],
    counties: [{ name: 'CountyName', value: '2' }],
    countries: [{ name: 'CountryName', value: '3' }],
    state: jest.fn()
};

jest.mock('options/location', () => ({
    useLocationOptions: () => mockLocationOptions
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
        const user = userEvent.setup();
        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Mortality information as of');

        await user.clear(asOf);
        await user.tab();

        expect(await findByText('The Mortality information as of is required.')).toBeInTheDocument();
    });

    it('should render the death information when deceased is true', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        const deceased = getByLabelText('Is the patient deceased?');

        await user.selectOptions(deceased, 'Y');

        expect(getByLabelText('Date of death')).toBeInTheDocument();
        expect(getByLabelText('Death city')).toBeInTheDocument();
        expect(getByLabelText('Death state')).toBeInTheDocument();
        expect(getByLabelText('Death county')).toBeInTheDocument();
        expect(getByLabelText('Death country')).toBeInTheDocument();
    });

    it('should reset the death information when deceased is reverted to false', async () => {
        const user = userEvent.setup();

        const { getByLabelText } = render(<Fixture />);
        // Set patient deceased Y
        await user.selectOptions(getByLabelText('Is the patient deceased?'), 'Y');

        // Enter data in previously hidden fields
        await user.type(getByLabelText('Date of death'), '12/01/2020');
        await user.type(getByLabelText('Death city'), 'City value');
        await user.selectOptions(getByLabelText('Death state'), '1');
        await user.selectOptions(getByLabelText('Death county'), '2');
        await user.selectOptions(getByLabelText('Death country'), '3');

        // Toggle deceased to N then back to Y
        await user.selectOptions(getByLabelText('Is the patient deceased?'), 'N');
        await user.selectOptions(getByLabelText('Is the patient deceased?'), 'Y');

        // Verify previously input data is cleared
        expect(getByLabelText('Date of death')).toHaveValue('');
        expect(getByLabelText('Death city')).toHaveValue('');
        expect(getByLabelText('Death state')).toHaveValue('');
        expect(getByLabelText('Death county')).toHaveValue('');
        expect(getByLabelText('Death country')).toHaveValue('');
    });
    it('should have accessibility description for the as of date field', () => {
        const { getByLabelText } = render(<Fixture />);
        const dateInput = getByLabelText('Mortality information as of');
        expect(dateInput).toHaveAttribute(
            'aria-description',
            "This field defaults to today's date and can be changed if needed."
        );
    });
});
