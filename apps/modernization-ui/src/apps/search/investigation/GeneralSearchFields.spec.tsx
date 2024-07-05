import { render, waitFor, screen } from '@testing-library/react';
import GeneralSearchFields from './GeneralSearchFields';
import { useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import userEvent from '@testing-library/user-event';
import { InvestigationFormContext } from './InvestigationFormContext';

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
        defaultValues
    });

    return (
        <InvestigationFormContext.Provider value={investigationForm}>
            <GeneralSearchFields />;
        </InvestigationFormContext.Provider>
    );
};

describe('GeneralSearchFields', () => {
    describe('Conditions', () => {
        it('should contain default selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);
            const multiSelectInputs = container.getElementsByClassName('multi-select-input');

            expect(multiSelectInputs[0].getElementsByClassName('usa-label')[0]).toHaveTextContent('Conditions');
            expect(multiSelectInputs[0].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
                '- Select -'
            );
        });
    });

    describe('Pregnancy Status', () => {
        it('should contain default selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const preg = screen.getByTestId('pregnancyStatus');
                expect(preg).toBeInTheDocument();
                expect(preg.getAttribute('placeholder')).toEqual('-Select-');
            });
        });

        it('should update the selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('pregnancyStatus');
                userEvent.selectOptions(element, 'NO');
                expect(element).toHaveTextContent('NO');
            });
        });
    });

    describe('Event ID Type', () => {
        it('should contain default selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('identification.type');
                expect(element).toBeInTheDocument();
                expect(element).toHaveTextContent('- Select -');
            });
        });

        it('should update the selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('identification.type');
                userEvent.selectOptions(element, 'Date of report');
                expect(element).toHaveTextContent('Date of report');
            });
        });
    });

    describe('Event Date Type', () => {
        it('should contain default selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('eventDate.type');
                expect(element).toBeInTheDocument();
                expect(element).toHaveTextContent('- Select -');
            });
        });

        it('should update the selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('eventDate.type');
                userEvent.selectOptions(element, 'Notification ID');
                expect(element).toHaveTextContent('Notification ID');
            });
        });
    });

    describe('Reporting Facility type', () => {
        it('should contain default selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('reportingFacility');
                expect(element).toBeInTheDocument();
                expect(element).toHaveTextContent('- Select -');
            });
        });

        it('should update the selection', async () => {
            const { container } = render(<InvestigationFormWithFields />);

            await waitFor(() => {
                const element = screen.getByTestId('reportingFacility');
                userEvent.selectOptions(element, 'Reporting Facility');
                expect(element).toHaveTextContent('Reporting Facility');
            });
        });
    });
});
