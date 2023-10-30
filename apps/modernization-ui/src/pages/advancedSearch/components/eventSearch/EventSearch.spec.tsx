import { MockedProvider } from '@apollo/react-testing';
import { render, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { EventSearch } from './EventSearch';
import userEvent from '@testing-library/user-event';

describe('EventSearch component tests', () => {
    it('should render event search form', async () => {
        const { getByTestId } = render(
            <MockedProvider>
                <BrowserRouter>
                    <EventSearch onSearch={() => {}} />
                </BrowserRouter>
            </MockedProvider>
        );
        const formEle = getByTestId('form');
        const searchButton = getByTestId('search');
        const clearButton = getByTestId('clear');
        const accordionEle = getByTestId('accordion');
        const eventTypeButtonEle = getByTestId('label');
        const accordionItemOneEle = getByTestId('accordionItem_event-type-section');
        const labelEle = getByTestId('label');
        const dropdownEle = getByTestId('dropdown');
        const dropdownOptionEles = dropdownEle.querySelectorAll('option');

        // Form should contain Search and Clear All buttons
        await waitFor(() => {
            expect(formEle).toContainElement(searchButton);
            expect(formEle).toContainElement(clearButton);
            expect(searchButton).toHaveTextContent('Search');
            expect(clearButton).toHaveTextContent('Clear all');

            // Accordion element should contain a wrapper element child
            expect(accordionEle).toContainElement(accordionItemOneEle);

            // Accordion wrapper element should contain button for Event type and dropdown with 2 options
            expect(accordionItemOneEle).toContainElement(labelEle);
            expect(accordionItemOneEle).toContainElement(dropdownEle);
            expect(dropdownEle).toContainElement(dropdownOptionEles[1]);
            expect(dropdownEle).toContainElement(dropdownOptionEles[2]);
            expect(eventTypeButtonEle).toHaveTextContent('Event type');
            expect(dropdownOptionEles[1]).toHaveTextContent('Investigation');
            expect(dropdownOptionEles[2]).toHaveTextContent('Laboratory report');
            expect(dropdownEle).toHaveAttribute('placeholder', '- Select -');
        });
    });

    it('should render investigation form', async () => {
        const { getByTestId } = render(
            <MockedProvider>
                <BrowserRouter>
                    <EventSearch onSearch={() => {}} />
                </BrowserRouter>
            </MockedProvider>
        );
        // Select Investigation
        userEvent.selectOptions(getByTestId('dropdown'), 'investigation');

        // Form should now contain `General search` and `Investigation criteria`
        const formEle = getByTestId('form');

        // General search
        const investigationGeneralSection = getByTestId('accordionButton_investigation-general-section');

        await waitFor(() => {
            expect(formEle).toContainElement(investigationGeneralSection);
            expect(investigationGeneralSection).toHaveTextContent('General search');

            // Investigation criteria
            const investigationCriteriaSection = getByTestId('accordionButton_investigation-criteria-section');
            expect(formEle).toContainElement(investigationCriteriaSection);
            expect(investigationCriteriaSection).toHaveTextContent('Investigation criteria');
        });
    });

    it('should render lab report form', async () => {
        const { getByTestId } = render(
            <MockedProvider>
                <BrowserRouter>
                    <EventSearch onSearch={() => {}} />
                </BrowserRouter>
            </MockedProvider>
        );
        // Select Investigation
        userEvent.selectOptions(getByTestId('dropdown'), 'labReport');

        // Form should now contain `General search` and `Investigation criteria`
        const formEle = getByTestId('form');

        // General search
        const investigationGeneralSection = getByTestId('accordionButton_lab-general-section');

        await waitFor(() => {
            expect(formEle).toContainElement(investigationGeneralSection);
            expect(investigationGeneralSection).toHaveTextContent('General search');

            // Investigation criteria
            const investigationCriteriaSection = getByTestId('accordionButton_lab-criteria-section');
            expect(formEle).toContainElement(investigationCriteriaSection);
            expect(investigationCriteriaSection).toHaveTextContent('Lab report criteria');
        });
    });
});
