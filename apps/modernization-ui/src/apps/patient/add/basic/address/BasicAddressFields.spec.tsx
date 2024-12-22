import { AddressEntry } from 'apps/patient/data';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicAddressFields } from './BasicAddressFields';
import { render, waitFor } from '@testing-library/react';
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
    const form = useForm<AddressEntry>({
        mode: 'onBlur',
        defaultValues: {
            address1: undefined,
            address2: undefined,
            city: undefined,
            state: undefined,
            zipcode: undefined,
            county: undefined,
            country: undefined,
            censusTract: undefined
        }
    });
    return (
        <FormProvider {...form}>
            <BasicAddressFields />
        </FormProvider>
    );
};

describe('when entering address section', () => {
    it('should render with proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Street address 1')).toBeInTheDocument();
        expect(getByLabelText('Street address 2')).toBeInTheDocument();
        expect(getByLabelText('City')).toBeInTheDocument();
        expect(getByLabelText('State')).toBeInTheDocument();
        expect(getByLabelText('Zip')).toBeInTheDocument();
        expect(getByLabelText('County')).toBeInTheDocument();
        expect(getByLabelText('Census tract')).toBeInTheDocument();
        expect(getByLabelText('Country')).toBeInTheDocument();
    });

    test.each([
        { value: '0000.00', valid: false },
        { value: '0001.00', valid: false },
        { value: '0001.01', valid: true },
        { value: '1000.00', valid: false },
        { value: '9999.99', valid: false },
        { value: '9999.98', valid: true },
        { value: '0001', valid: true },
        { value: '9999', valid: true },
        { value: '0000', valid: false },
        { value: '9999.00', valid: false },
        { value: '0001.99', valid: false },
        { value: '1234.56', valid: true }
    ])('should validate Census Tract format for value: $value', async ({ value, valid }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const censusTractInput = getByLabelText('Census tract');

        userEvent.clear(censusTractInput);
        userEvent.paste(censusTractInput, value);
        userEvent.tab();

        const validationMessage =
            'The Census tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            if (valid) {
                expect(validationError).not.toBeInTheDocument();
            } else {
                expect(validationError).toBeInTheDocument();
            }
        });
    });
    it('should validate address 1', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const address = getByLabelText('Street address 1');
        userEvent.clear(address);
        userEvent.paste(
            address,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhjhsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f'
        );
        userEvent.tab();

        const validationMessage = 'The Street address 1 only allows 100 characters';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            expect(validationError).toBeInTheDocument();
        });
    });
    it('should validate address 2', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const address = getByLabelText('Street address 2');
        userEvent.clear(address);
        userEvent.paste(
            address,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhjhsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f'
        );
        userEvent.tab();

        const validationMessage = 'The Street address 2 only allows 100 characters';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            expect(validationError).toBeInTheDocument();
        });
    });
    it('should validate city', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const city = getByLabelText('City');
        userEvent.clear(city);
        userEvent.paste(
            city,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhjhsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f'
        );
        userEvent.tab();

        const validationMessage = 'The City only allows 100 characters';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            expect(validationError).toBeInTheDocument();
        });
    });
});
