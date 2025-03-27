import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { NameInformationEntry } from '../entry';
import { NameEntryFields } from './NameEntryFields';

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};

jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
    const form = useForm<NameInformationEntry>({
        mode: 'onBlur',
        defaultValues: {
            first: undefined,
            middle: undefined,
            last: undefined,
            suffix: undefined
        }
    });

    return (
        <FormProvider {...form}>
            <NameEntryFields sizing={props.sizing} />
        </FormProvider>
    );
};

describe('when entering name information', () => {
    it('should render all input fields', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('First')).toBeInTheDocument();
        expect(getByLabelText('Middle')).toBeInTheDocument();
        expect(getByLabelText('Last')).toBeInTheDocument();
        expect(getByLabelText('Suffix')).toBeInTheDocument();
    });

    it('should render all input fields with the small sizing when sizing is set to small.', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        expect(getByText('First').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Middle').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Last').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Suffix').parentElement?.parentElement).toHaveClass('small');
    });

    it('should validate last name', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getByText } = render(<Fixture />);

        const last = getByLabelText('Last');
        await user.type(
            last,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdhfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f{tab}'
        );

        expect(getByText(/The Last name only allows 50 characters max/)).toBeInTheDocument();
    });
    it('should validate middle name', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getByText } = render(<Fixture />);

        const middle = getByLabelText('Middle');
        await user.type(
            middle,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdhfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f{tab}'
        );

        expect(getByText(/The Middle name only allows 50 characters max/)).toBeInTheDocument();
    });
    it('should validate first name', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getByText } = render(<Fixture />);

        const first = getByLabelText('First');

        await user.type(
            first,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdhfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f{tab}'
        );

        expect(getByText(/The First name only allows 50 characters max/)).toBeInTheDocument();
    });
});
