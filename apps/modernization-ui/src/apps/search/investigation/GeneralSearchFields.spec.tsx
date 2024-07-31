import { render, waitFor, screen } from '@testing-library/react';
import GeneralSearchFields from './GeneralSearchFields';
import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import userEvent from '@testing-library/user-event';

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

    const form = useForm<InvestigationFilterEntry>({
        defaultValues,
        mode: 'all'
    });

    return (
        <FormProvider {...form}>
            <GeneralSearchFields />;
        </FormProvider>
    );
};

describe('GeneralSearchFields', () => {
    describe('Pregnancy Status', () => {
        it('should contain default selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const preg = screen.getByTestId('pregnancyStatus');
                expect(preg).toBeInTheDocument();
                expect(preg.getAttribute('placeholder')).toEqual('-Select-');
            });
        });

        it('should update the selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('pregnancyStatus');
                userEvent.selectOptions(element, 'NO');
                expect(element).toHaveTextContent('NO');
            });
        });
    });

    describe('Event ID Type', () => {
        it('should contain default selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('identification.type');
                expect(element).toBeInTheDocument();
                expect(element).toHaveTextContent('- Select -');
            });
        });

        it('should update the selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('identification.type');
                userEvent.selectOptions(element, 'Notification ID');
                expect(element).toHaveTextContent('Notification ID');
            });
        });
    });

    describe('Event Date Type', () => {
        it('should contain default selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('eventDate.type');
                expect(element).toBeInTheDocument();
                expect(element).toHaveTextContent('- Select -');
            });
        });

        it('should update the selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('eventDate.type');
                userEvent.selectOptions(element, 'Investigation closed date');
                expect(element).toHaveTextContent('Investigation closed date');
            });
        });
    });

    describe('Reporting Facility type', () => {
        it('should contain default selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('reportingFacility');
                expect(element).toBeInTheDocument();
                expect(element).toHaveTextContent('- Select -');
            });
        });

        it('should update the selection', async () => {
            render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('reportingFacility');
                userEvent.selectOptions(element, 'Reporting Facility');
                expect(element).toHaveTextContent('Reporting Facility');
            });
        });

        it('should not display event id', () => {
            const { queryByLabelText } = render(<InvestigationFormWithFields />);
            const eventIdField = queryByLabelText('Event ID');
            expect(eventIdField).toBeNull();
        });

        it('should display event id once event id type is selected', () => {
            const { getByLabelText, queryByLabelText } = render(<InvestigationFormWithFields />);

            const eventTypeField = getByLabelText('Event ID type');
            userEvent.selectOptions(eventTypeField, 'ABCS_CASE_ID');

            const eventIdField = queryByLabelText('Event ID');
            expect(eventIdField).toBeInTheDocument();
        });
    });
});
