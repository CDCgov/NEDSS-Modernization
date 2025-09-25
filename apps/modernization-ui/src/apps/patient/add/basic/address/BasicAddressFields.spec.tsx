import { vi } from 'vitest';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { AddressEntry } from 'apps/patient/data';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicAddressFields } from './BasicAddressFields';
import { LocationOptions } from 'options/location';

const mockState = vi.fn();

const mockLocationOptions: LocationOptions = {
    states: [{ name: 'StateName', value: '1' }],
    counties: [{ name: 'CountyName', value: '2' }],
    countries: [{ name: 'CountryName', value: '3' }],
    state: mockState
};

vi.mock('options/location', () => ({
    useLocationOptions: () => mockLocationOptions
}));

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
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
            <BasicAddressFields sizing={props.sizing} />
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

    it('should render the proper labels as small when the sizing is set to small', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        expect(getByText('Street address 1').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Street address 2').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('City').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('State').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Zip').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('County').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Census tract').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Country').parentElement?.parentElement).toHaveClass('small');
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
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);
        const censusTractInput = getByLabelText('Census tract');

        await user.clear(censusTractInput);
        await user.type(censusTractInput, value);
        await user.tab();

        const validationMessage =
            'The Census tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.';

        const validationError = queryByText(validationMessage);
        if (valid) {
            expect(validationError).not.toBeInTheDocument();
        } else {
            expect(validationError).toBeInTheDocument();
        }
    });
    it('should validate address 1', async () => {
        const user = userEvent.setup({ delay: null });

        const { getByLabelText, queryByText } = render(<Fixture />);

        const address = getByLabelText('Street address 1');
        await user.clear(address);
        await user.type(
            address,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhjhsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f'
        );
        await user.tab();

        const validationMessage = 'The Street address 1 only allows 100 characters max.';

        const validationError = queryByText(validationMessage);
        expect(validationError).toBeInTheDocument();
    });
    it('should validate address 2', async () => {
        const user = userEvent.setup({ delay: null });
        const { getByLabelText, queryByText } = render(<Fixture />);

        const address = getByLabelText('Street address 2');
        await user.clear(address);
        await user.type(
            address,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhjhsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f'
        );
        await user.tab();

        const validationMessage = 'The Street address 2 only allows 100 characters max.';

        const validationError = queryByText(validationMessage);
        expect(validationError).toBeInTheDocument();
    });
    it('should validate city', async () => {
        const user = userEvent.setup({ delay: null });
        const { getByLabelText, queryByText } = render(<Fixture />);

        const city = getByLabelText('City');
        await user.clear(city);
        await user.type(
            city,
            'hsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhjhsdfjhsjfjshkfhskhfkhskjfhkjshfhsdskjhfjsdhfjshjfhjsdhfsdhdfjsdhfhjshjfsdhfsdfhjsfjhsjdfsfasdjhvmbsauhcjdbkashjiodjbkdsnachudihbjsnjacibhj dkacindijsnjpasdfilksbdvsdovbadkhv zcasjkfasnj hasb fkasj asjks s jdasjaksdb fbashf asfasfaskbf as faskj bfkdsbfkasb f'
        );
        await user.tab();

        const validationMessage = 'The City only allows 100 characters max.';

        const validationError = queryByText(validationMessage);
        expect(validationError).toBeInTheDocument();
    });
});
