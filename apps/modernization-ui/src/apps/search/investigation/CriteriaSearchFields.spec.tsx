import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import userEvent from '@testing-library/user-event';
import CriteriaSearchFields from './CriteriaSearchFields';

jest.mock('options/concepts/useConceptOptions', () => ({
    useConceptOptions: () => ({ options: [] })
}));

const InvestigationFormWithFields = () => {
    const investigationForm = useForm<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>({
        mode: 'onBlur'
    });

    return (
        <FormProvider {...investigationForm}>
            <CriteriaSearchFields />;
        </FormProvider>
    );
};

describe('CriteriaSearchFields', () => {
    describe('Investigation Status', () => {
        it('should exist', () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Investigation status' });
            expect(select).toBeInTheDocument();
        });

        it('should update the selection', async () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Investigation status' });

            const user = userEvent.setup();
            await user.selectOptions(select, 'Open');

            expect(getByRole('option', { name: 'Open', selected: true })).toBeInTheDocument();
        });
    });

    it('should contain Outbreak name', () => {
        const { getByText } = render(<InvestigationFormWithFields />);

        expect(getByText('Outbreak name')).toBeInTheDocument();
    });

    it('should contain Case status', () => {
        const { getByText } = render(<InvestigationFormWithFields />);

        expect(getByText('Case status')).toBeInTheDocument();
    });
});
