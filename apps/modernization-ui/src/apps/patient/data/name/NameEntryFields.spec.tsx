import { FormProvider, useForm } from 'react-hook-form';
import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { NameEntry } from './entry';
import { NameEntryFields } from './NameEntryFields';

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
    const form = useForm<NameEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            type: undefined,
            prefix: undefined,
            first: '',
            middle: '',
            last: '',
            secondMiddle: '',
            secondLast: '',
            suffix: undefined,
            degree: undefined
        }
    });

    return (
        <FormProvider {...form}>
            <NameEntryFields />
        </FormProvider>
    );
};

describe('when entering patient name demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Name as of')).toBeInTheDocument();
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

        const asOf = getByLabelText('Name as of');

        await user.click(asOf);
        await user.tab();

        expect(await findByText('The Name as of is required.')).toBeInTheDocument();
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

        const asOf = getByLabelText('Name as of');
        const type = getByLabelText('Type');

        await user.type(asOf, '01/20/2020');
        await user.tab();
        await user.selectOptions(type, 'AN');
        await user.tab();

        await waitFor(() => {
            expect(queryByText('The Name as of is required')).not.toBeInTheDocument();
            expect(queryByText('The Type is required')).not.toBeInTheDocument();
        });
    });
});
