import { fireEvent, render, waitFor, screen } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { act } from 'react-dom/test-utils';
import { FormProvider, useForm } from 'react-hook-form';
import { PhoneEmailEntry } from '../entry';
import { PhoneEmailEntryFields } from './PhoneEmailEntryFields';
import userEvent from '@testing-library/user-event';

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/profile/phoneEmail/usePatientPhoneCodedValues', () => ({
    usePatientPhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const Fixture = () => {
    const form = useForm<PhoneEmailEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            type: undefined,
            use: undefined,
            countryCode: '',
            phoneNumber: '',
            extension: '',
            email: '',
            url: '',
            comment: ''
        }
    });
    return (
        <FormProvider {...form}>
            <PhoneEmailEntryFields />
        </FormProvider>
    );
};

describe('PhoneEmailEntryFields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Phone & email as of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Use')).toBeInTheDocument();
        expect(getByLabelText('Country code')).toBeInTheDocument();
        expect(getByLabelText('Phone number')).toBeInTheDocument();
        expect(getByLabelText('Extension')).toBeInTheDocument();
        expect(getByLabelText('Email')).toBeInTheDocument();
        expect(getByLabelText('Phone & email comments')).toBeInTheDocument();
    });

    it('should require type', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const typeInput = getByLabelText('Type');
        act(() => {
            fireEvent.blur(typeInput);
        });
        await waitFor(() => {
            expect(getByText('Type is required.')).toBeInTheDocument();
        });
    });

    it('should require use', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const useInput = getByLabelText('Use');
        act(() => {
            fireEvent.blur(useInput);
        });
        await waitFor(() => {
            expect(getByText('Use is required.')).toBeInTheDocument();
        });
    });

    it('should require as of', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');
        act(() => {
            fireEvent.blur(asOf);
        });
        await waitFor(() => {
            expect(getByText('As of date is required.')).toBeInTheDocument();
        });
    });

    it('should be valid with as of, type, and use', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        await screen.findByText('Use');
        act(() => {
            userEvent.paste(asOf, '01/20/2020');
            fireEvent.blur(asOf);
            userEvent.selectOptions(use, 'H');
            fireEvent.blur(use);
            userEvent.selectOptions(type, 'PH');
            fireEvent.blur(type);
        });

        await waitFor(() => {
            expect(queryByText('Type is required.')).not.toBeInTheDocument();
            expect(queryByText('As of date is required.')).not.toBeInTheDocument();
            expect(queryByText('Use is required.')).not.toBeInTheDocument();
        });
    });
});
