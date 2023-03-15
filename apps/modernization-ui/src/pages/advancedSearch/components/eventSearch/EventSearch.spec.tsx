import { render } from '@testing-library/react';
import { MockedProvider } from '@apollo/react-testing';
import { BrowserRouter } from 'react-router-dom';
import { InvestigationFilter, LabReportFilter } from '../../../../generated/graphql/schema';
import { SEARCH_TYPE } from '../../AdvancedSearch';
import { EventSearch } from './EventSearch';

describe('EventSearch component tests', () => {
    it('should render event search form', () => {
        const mockOnSearch = (filter: InvestigationFilter | LabReportFilter, type: SEARCH_TYPE) => {};
        const mockClearAll = () => {};
        const { container, getByLabelText, getByTestId, getAllByTestId } = render(
            <MockedProvider>
                <BrowserRouter>
                    <EventSearch onSearch={mockOnSearch} clearAll={mockClearAll} />
                </BrowserRouter>
            </MockedProvider>
        );
        const formEle = getByTestId('form');
        const accordionEle = getByTestId('accordion');
        const eventTypeButtonEle = getByTestId('accordionButton_1');
        const accordionItemOneEle = getByTestId('accordionItem_1');
        const labelEle = getByTestId('label');
        const dropdownEle = getByTestId('dropdown');
        const dropdownOptionEles = dropdownEle.querySelectorAll('option');
        const gridEle = getAllByTestId('grid')[0];
        const buttonEles = getAllByTestId('button');

        // Form element should contain an accordion and grid
        expect(formEle).toContainElement(accordionEle);
        expect(formEle).toContainElement(gridEle);

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

        // Grid element should contain buttons for Search and Clear all
        expect(gridEle).toContainElement(buttonEles[0]);
        expect(gridEle).toContainElement(buttonEles[1]);
        expect(buttonEles[0]).toHaveTextContent('Search');
        expect(buttonEles[1]).toHaveTextContent('Clear all');
    });
});
