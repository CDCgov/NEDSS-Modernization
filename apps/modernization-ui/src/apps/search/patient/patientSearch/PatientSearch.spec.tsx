import { fireEvent, render, waitFor } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { PersonFilter, RecordStatus } from 'generated/graphql/schema';
import { PatientSearch } from './PatientSearch';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import userEvent from '@testing-library/user-event';

const sampleSearchFunction = jest.fn();
const sampleClearFunction = jest.fn();

describe('PatientSearch component tests', () => {
    it('should render 5 accordions each with a specific form', () => {
        const { result } = renderHook(() => useForm());
        const sampleSearchFunction = (data: PersonFilter) => {};
        const sampleClearFunction = () => {};
        let sampleData;
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={sampleData}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );
        const accordionH4Elements = container.querySelectorAll('h3.accordian-item');
        expect(accordionH4Elements.length).toBe(5);
        expect(accordionH4Elements[0].textContent).toBe('Basic information');
        expect(accordionH4Elements[1].textContent).toBe('Address');
        expect(accordionH4Elements[2].textContent).toBe('Contact');
        expect(accordionH4Elements[3].textContent).toBe('ID');
        expect(accordionH4Elements[4].textContent).toBe('Race / Ethnicity');
    });

    it('should have the basic information accorgion expanded by default and rest all closed', () => {
        const sampleSearchFunction = (data: PersonFilter) => {};
        const sampleClearFunction = () => {};
        let sampleData;
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={sampleData}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );
        const accordionButtonElements = container.querySelectorAll('h3.accordian-item button');
        expect(accordionButtonElements[0].getAttribute('aria-expanded')).toBe('true');
        expect(accordionButtonElements[1].getAttribute('aria-expanded')).toBe('false');
        expect(accordionButtonElements[2].getAttribute('aria-expanded')).toBe('false');
        expect(accordionButtonElements[3].getAttribute('aria-expanded')).toBe('false');
        expect(accordionButtonElements[4].getAttribute('aria-expanded')).toBe('false');
    });

    it('should only have the "Active" record status checked by default', () => {
        const sampleSearchFunction = (data: PersonFilter) => {};
        const sampleClearFunction = () => {};
        let sampleData;
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={sampleData}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );
        const activeCheckbox = container.querySelector('#record-status-active') as HTMLInputElement;
        const deletedCheckbox = container.querySelector('#record-status-deleted') as HTMLInputElement;
        const superCededCheckbox = container.querySelector('#record-status-superceded') as HTMLInputElement;
        expect(activeCheckbox.checked).toBe(true);
        expect(deletedCheckbox.checked).toBe(false);
        expect(superCededCheckbox.checked).toBe(false);
    });

    it('should set record status checkboxes from incoming data', async () => {
        const sampleSearchFunction = (data: PersonFilter) => {};
        const sampleClearFunction = () => {};
        const sampleData: PersonFilter = { recordStatus: [RecordStatus.LogDel, RecordStatus.Superceded] };
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={sampleData}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );
        const activeCheckbox = container.querySelector('#record-status-active') as HTMLInputElement;
        const deletedCheckbox = container.querySelector('#record-status-deleted') as HTMLInputElement;
        const superCededCheckbox = container.querySelector('#record-status-superceded') as HTMLInputElement;
        await waitFor(() => {
            expect(activeCheckbox.checked).toBe(false);
            expect(deletedCheckbox.checked).toBe(true);
            expect(superCededCheckbox.checked).toBe(true);
        });
    });

    it('should return the selected record status on submit', async () => {
        const sampleSearchFunction = (data: PersonFilter) => {
            // Verify all 3 record status returned

            expect(data.recordStatus).toMatchObject([
                RecordStatus.LogDel,
                RecordStatus.Superceded,
                RecordStatus.Active
            ]);
        };

        const sampleClearFunction = () => {};
        // Load page with Deleted and Superceded checked
        const sampleData: PersonFilter = { recordStatus: [RecordStatus.LogDel, RecordStatus.Superceded] };
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={sampleData}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );
        // Click on Active to select it
        await waitFor(() => {
            const activeCheckbox = container.querySelector('#record-status-active') as HTMLInputElement;
            activeCheckbox.click();
            // Click submit
            const submitButton = container.querySelector('button[type="submit"]') as HTMLButtonElement;
            submitButton.click();
        });
    });

    it('should display an error when no record status is selected', async () => {
        const sampleSearchFunction = (data: PersonFilter) => {};
        const sampleClearFunction = () => {};
        const sampleData: PersonFilter = { recordStatus: [RecordStatus.Active] };
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={sampleData}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );
        // Error message should be hidden
        let errorMessage = container.querySelector('#record-status-error-message') as HTMLSpanElement;
        expect(errorMessage).toBeFalsy();

        // Click on Active to de-select it (no RecordStatus selected)
        const activeCheckbox = container.querySelector('#record-status-active') as HTMLInputElement;
        activeCheckbox.click();

        // Error message should be visible
        await waitFor(() => {
            errorMessage = container.querySelector('#record-status-error-message') as HTMLSpanElement;
            expect(errorMessage).toBeTruthy();
        });

        // Click Active to select it
        activeCheckbox.click();

        // Error message should be hidden
        await waitFor(() => {
            errorMessage = container.querySelector('#record-status-error-message') as HTMLSpanElement;
            expect(errorMessage).toBeFalsy();
        });
    });

    it('should call handleSubmission with cleaned filter data on form submit', async () => {
        const sampleSearchFunction = jest.fn(); // Mock function
        const sampleData: PersonFilter = { recordStatus: [RecordStatus.LogDel, RecordStatus.Superceded] };
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={sampleData}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );

        const form = container.querySelector('form');
        fireEvent.submit(form as HTMLFormElement);

        await waitFor(() => {
            expect(sampleSearchFunction).toHaveBeenCalledWith({
                recordStatus: [RecordStatus.LogDel, RecordStatus.Superceded]
            });
        });
    });

    it('should call clearAll and reset form on Clear All button click', async () => {
        const { container } = render(
            <SkipLinkProvider>
                <PatientSearch
                    handleSubmission={sampleSearchFunction}
                    personFilter={undefined}
                    clearAll={sampleClearFunction}
                />
            </SkipLinkProvider>
        );

        const clearAllButton = container.querySelector('button[aria-label="Clear all search criteria"]');
        userEvent.click(clearAllButton as Element);

        await waitFor(() => {
            expect(sampleClearFunction).toHaveBeenCalled();
        });
    });
});
