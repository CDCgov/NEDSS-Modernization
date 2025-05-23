import { FormProvider, useForm } from 'react-hook-form';
import { MockedProvider } from '@apollo/react-testing';
import { render } from '@testing-library/react';
import { Address } from './Address';
import { PatientCriteriaEntry } from '../criteria';

const Fixture = () => {
    const form = useForm<PatientCriteriaEntry>({
        mode: 'onBlur',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    });

    return (
        <MockedProvider>
            <FormProvider {...form}>
                <Address />
            </FormProvider>
        </MockedProvider>
    );
};

describe('when Address renders', () => {
    //  this test would be more effective if it checked for the existence of input labels and asserts accessibility settings.

    it('should render 3 input fields', () => {
        const { container } = render(<Fixture />);
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toEqual(3);
    });
});
