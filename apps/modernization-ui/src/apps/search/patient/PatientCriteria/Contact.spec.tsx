import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import { Contact } from './Contact';
import { PatientCriteriaEntry } from '../criteria';

import { MockedProvider } from '@apollo/react-testing';

const Fixture = () => {
    const form = useForm<PatientCriteriaEntry>({
        mode: 'onChange',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    });

    return (
        <MockedProvider>
            <FormProvider {...form}>
                <Contact />
            </FormProvider>
        </MockedProvider>
    );
};

describe('when Address renders', () => {
    //  this test would be more effective if it checked for the existence of input labels and asserts accessibility settings.

    it('should render 2 input fields', () => {
        const { container } = render(<Fixture />);
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(2);
    });
});
