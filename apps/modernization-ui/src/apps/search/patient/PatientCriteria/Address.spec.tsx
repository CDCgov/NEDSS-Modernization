import { MockedProvider } from '@apollo/client/testing';
import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';

import { PatientCriteriaEntry } from '../criteria';

import { Address } from './Address';

vi.mock('options/location', () => ({
    useStateOptions: () => [],
}));

const Fixture = () => {
    const form = useForm<PatientCriteriaEntry>({
        mode: 'onBlur',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] },
    });

    return (
        <MockedProvider mocks={[]}>
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
