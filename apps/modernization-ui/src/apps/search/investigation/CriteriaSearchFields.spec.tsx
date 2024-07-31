import { render, waitFor, screen } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import userEvent from '@testing-library/user-event';
import CriteriaSearchFields from './CriteriaSearchFields';

const InvestigationFormWithFields = () => {
    const defaultSelectable = { name: '', value: '', label: '' };
    const defaultValues: InvestigationFilterEntry = {
        createdBy: defaultSelectable,
        updatedBy: defaultSelectable,
        investigator: defaultSelectable,
        pregnancyStatus: defaultSelectable,
        investigationStatus: defaultSelectable,
        jurisdictions: [defaultSelectable],
        conditions: [defaultSelectable],
        caseStatuses: [defaultSelectable],
        notificationStatuses: [defaultSelectable],
        outbreaks: [defaultSelectable],
        processingStatuses: [defaultSelectable],
        programAreas: [],
        reportingFacility: defaultSelectable
    };

    const investigationForm = useForm<InvestigationFilterEntry>({
        defaultValues,
        mode: 'all'
    });

    return (
        <FormProvider {...investigationForm}>
            <CriteriaSearchFields />;
        </FormProvider>
    );
};

describe('CriteriaSearchFields', () => {
    describe('Investigation Status', () => {
        it('should contain default selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('investigationStatus');
                expect(element).toBeInTheDocument();
                expect(element.getAttribute('placeholder')).toEqual('-Select-');
            });
        });

        it('should update the selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('investigationStatus');
                userEvent.selectOptions(element, 'Open');
                expect(element).toHaveTextContent('Open');
            });
        });
    });

    describe('Outbreaks', () => {
        it('should not contain default selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const elements = container.getElementsByClassName('usa-label');

                expect(elements[0]).toBeInTheDocument();
                expect(elements[0].getAttribute('name')).toEqual(null);
            });
        });
    });
});
