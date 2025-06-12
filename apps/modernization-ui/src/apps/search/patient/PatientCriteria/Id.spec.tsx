import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { Id } from './Id';
import { FormProvider, useForm } from 'react-hook-form';
import { PatientCriteriaEntry } from '../criteria';

vi.mock('options/concepts', () => ({
    useConceptOptions: () => ({
        options: [
            { value: 'EI_TYPE_PAT', label: 'Patient ID' },
            { value: 'EI_TYPE_NAT', label: 'National ID' }
        ]
    })
}));

describe('When Id renders', () => {
    it('should render 1 select', () => {
        const Wrapper = () => {
            const methods = useForm();
            return (
                <FormProvider {...methods}>
                    <Id />
                </FormProvider>
            );
        };
        const { container } = render(<Wrapper />);
        const options = container.getElementsByTagName('select');
        expect(options).toHaveLength(1);
    });
});

describe('When identificationType is selected', () => {
    it('should render 2 select', () => {
        const Wrapper = () => {
            const methods = useForm<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>({
                defaultValues: { identificationType: { value: 'ASSDF' } }
            });
            return (
                <FormProvider {...methods}>
                    <Id />
                </FormProvider>
            );
        };
        const { container } = render(<Wrapper />);
        const options = container.getElementsByTagName('label');
        expect(options).toHaveLength(2);
    });
});
