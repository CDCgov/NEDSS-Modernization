import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import userEvent from '@testing-library/user-event';
import CriteriaSearchFields from './CriteriaSearchFields';

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

        it('should update the selection', () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Investigation status' });

            userEvent.selectOptions(select, 'Open');

            expect(getByRole('option', { name: 'Open', selected: true })).toBeInTheDocument();
        });
    });

    it('should contain Outbeak name', () => {
        const { getByText } = render(<InvestigationFormWithFields />);

        expect(getByText('Outbreak name')).toBeInTheDocument();
    });

    it('should contain Case status', () => {
        const { getByText } = render(<InvestigationFormWithFields />);

        expect(getByText('Case status')).toBeInTheDocument();
    });
});
