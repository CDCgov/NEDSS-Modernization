import { render } from '@testing-library/react';
import { Contact } from './Contact';
import { PatientCriteriaEntry } from '../criteria';
import { FormProvider, useForm } from 'react-hook-form';
import { renderHook } from '@testing-library/react-hooks';
import { MockedProvider } from '@apollo/react-testing';

const { result } = renderHook(() =>
    useForm<PatientCriteriaEntry>({
        mode: 'onChange',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    })
);

const setup = () => {
    return render(
        <MockedProvider>
            <FormProvider {...result.current}>
                <Contact />
            </FormProvider>
        </MockedProvider>
    );
};

describe('when Address renders', () => {
    it('should render 2 input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(2);
    });
});
