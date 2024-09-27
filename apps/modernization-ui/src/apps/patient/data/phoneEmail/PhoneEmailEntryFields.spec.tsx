import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { PhoneEmailEntry } from '../entry';
import { PhoneEmailEntryFields } from './PhoneEmailEntryFields';

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
});
