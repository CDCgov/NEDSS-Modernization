import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { PatientDemographics } from '../demographics';
import { NameDemographicFields } from './NameDemographicFields';

const mockNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};

jest.mock('./useNameCodedValues', () => ({
    useNameCodedValues: () => mockNameCodedValues
}));

const Fixture = () => {
    const form = useForm<PatientDemographics>({
        mode: 'onBlur'
    });

    return (
        <FormProvider {...form}>
            <NameDemographicFields />
        </FormProvider>
    );
};

describe('when entering patient name demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Prefix')).toBeInTheDocument();
        expect(getByLabelText('First')).toBeInTheDocument();
        expect(getByLabelText('Middle')).toBeInTheDocument();
        expect(getByLabelText('Last')).toBeInTheDocument();
        expect(getByLabelText('Second middle')).toBeInTheDocument();
        expect(getByLabelText('Second last')).toBeInTheDocument();
        expect(getByLabelText('Suffix')).toBeInTheDocument();
        expect(getByLabelText('Degree')).toBeInTheDocument();
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
        const { getByLabelText, findByText } = render(<Fixture />);

        const type = getByLabelText('Type');

        await user.click(type);
        await user.tab();

        expect(await findByText('The Type is required.')).toBeInTheDocument();
    });

    it('should be valid with as of, race', async () => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('As of');
        const type = getByLabelText('Type');

        await user
            .type(asOf, '01/20/2020{tab}')
            .then(() => user.selectOptions(type, 'AN'))
            .then(() => user.tab());

        expect(queryByText('The As of is required')).not.toBeInTheDocument();
        expect(queryByText('The Type is required')).not.toBeInTheDocument();
    });
});
