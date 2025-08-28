import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { IdentificationDemographic } from '../identifications';
import { IdentificationDemographicFields } from './IdentificationDemographicFields';

const Fixture = () => {
    const form = useForm<IdentificationDemographic>({
        mode: 'onBlur'
    });
    return (
        <FormProvider {...form}>
            <IdentificationDemographicFields
                options={{
                    types: [{ name: 'Account number', value: 'AN' }],
                    authorities: [{ name: 'Assigning auth', value: 'AA' }]
                }}
            />
        </FormProvider>
    );
};

describe('when entering patient identification demographics', () => {
    it('should render the proper labels', async () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Assigning authority')).toBeInTheDocument();
        expect(getByLabelText('ID value')).toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('As of');

        await user.click(asOf);
        await user.tab();

        expect(await findByText('The As of is required.')).toBeInTheDocument();
    });

    it('should have accessibility description for the as of date field', () => {
        const { getByLabelText } = render(<Fixture />);

        const dateInput = getByLabelText('As of');

        expect(dateInput).toHaveAttribute(
            'aria-description',
            'This date defaults to today and can be changed if needed'
        );
    });

    it('should require type', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getByText } = render(<Fixture />);

        const typeInput = getByLabelText('Type');

        await user.click(typeInput);
        await user.tab();

        expect(getByText('The Type is required.')).toBeInTheDocument();
    });

    it('should require id value', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const valueInput = getByLabelText('ID value');

        await user.click(valueInput);
        await user.tab();

        expect(getByText('The ID value is required.')).toBeInTheDocument();
    });

    it('should be valid with as of, type, and id value', async () => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('As of');
        const type = getByLabelText('Type');
        const idValue = getByLabelText('ID value');

        await user.type(asOf, '01/20/2020');
        await user.tab();
        await user.selectOptions(type, 'AN');
        await user.tab();
        await user.type(idValue, '1234');
        await user.tab();

        expect(queryByText('The Type is required.')).not.toBeInTheDocument();
        expect(queryByText('The As of is required.')).not.toBeInTheDocument();
        expect(queryByText('The ID value is required.')).not.toBeInTheDocument();
    });
});
