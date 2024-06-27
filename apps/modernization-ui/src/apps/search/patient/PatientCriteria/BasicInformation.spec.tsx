import { render } from '@testing-library/react';
import { BasicInformation } from './BasicInformation';
import { PatientCriteriaEntry } from '../criteria';
import { useForm } from 'react-hook-form';

const Component = () => {
    const {control, formState: {isValid}} = useForm<PatientCriteriaEntry>({
            defaultValues: {
                status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }]
            },
            mode: 'onBlur'
        });
    return <BasicInformation control={control} handleRecordStatusChange={jest.fn()} />;
};

const setup = () => {
    return render(
        <Component />
    );
};

describe('when Basic information renders', () => {
    it('should render 8 input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(8);
    });
});