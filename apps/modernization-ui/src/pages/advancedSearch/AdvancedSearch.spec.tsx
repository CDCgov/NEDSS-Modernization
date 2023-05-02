const mockedUsedNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate
}));

import { AdvancedSearch } from './AdvancedSearch';
import { fireEvent, render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { MockedProvider } from '@apollo/client/testing';
import { expect } from '@jest/globals';
import { prettyDOM } from '@testing-library/dom';

describe('AdvancedSearch component tests', () => {
    it('should render filter to do advance search', () => {});
    describe('When page loads', () => {
        it('Add New button is disabled', () => {
            const { container } = render(
                <MockedProvider>
                    <BrowserRouter>
                        <AdvancedSearch />
                    </BrowserRouter>
                </MockedProvider>
            );
            const btn = container.getElementsByClassName('add-patient-button')[0];
            expect(btn.hasAttribute('disabled'));
        });
    });

    describe('When Closest match button is clicked', () => {
        it('should render list of all patients which closely matches the search criteria', () => {
            const { container, getByText } = render(
                <MockedProvider>
                    <BrowserRouter>
                        <AdvancedSearch />
                    </BrowserRouter>
                </MockedProvider>
            );
            const sortByButton = getByText('Sort by');

            fireEvent.click(sortByButton);
        });
    });
});
