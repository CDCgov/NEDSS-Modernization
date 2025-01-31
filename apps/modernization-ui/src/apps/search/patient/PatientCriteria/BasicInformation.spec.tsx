import { render } from '@testing-library/react';
import { BasicInformation } from './BasicInformation';
import { PatientCriteriaEntry } from '../criteria';
import { FormProvider, useForm } from 'react-hook-form';
import { renderHook } from '@testing-library/react-hooks';

const { result } = renderHook(() =>
    useForm<PatientCriteriaEntry>({
        mode: 'onChange',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    })
);

const setup = () => {
    return render(
        <FormProvider {...result.current}>
            <BasicInformation />
        </FormProvider>
    );
};

describe('when Basic information renders', () => {
    it('should render 8 input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(11);
    });

    it('should have helper text for patient ID', () => {
        const { getByText } = setup();
        expect(getByText('Separate IDs by commas, semicolons, or spaces')).toBeInTheDocument();
    });
});
