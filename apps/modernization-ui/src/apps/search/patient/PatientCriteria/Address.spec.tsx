import { render } from '@testing-library/react';
import { Address } from './Address';
import { PatientCriteriaEntry } from '../criteria';
import { FormProvider, useForm } from 'react-hook-form';
import { renderHook } from '@testing-library/react-hooks';
import { MockedProvider } from '@apollo/react-testing';

const { result } = renderHook(() =>
    useForm<PatientCriteriaEntry>({
        mode: 'onBlur',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    })
);

const setup = () => {
    return render (
        <MockedProvider>
            <FormProvider {...result.current}>
                <Address />
            </FormProvider>
        </MockedProvider>
    );
};

describe('when Address renders', () => {
    it('should render 3 input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toEqual(3);
    });
});
