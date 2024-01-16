const mockedUsedNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate
}));

import { AdvancedSearch } from 'apps/search/advancedSearch/AdvancedSearch';
import { fireEvent, render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { MockedProvider } from '@apollo/client/testing';
import { expect } from '@jest/globals';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';

describe('AdvancedSearch component tests', () => {
    it('should render filter to do advance search', () => {});
    describe('When page loads', () => {
        it('Add New button is disabled', () => {
            const { container } = render(
                <MockedProvider>
                    <BrowserRouter>
                        <SkipLinkProvider>
                            <AdvancedSearch />
                        </SkipLinkProvider>
                    </BrowserRouter>
                </MockedProvider>
            );
            const btn = container.getElementsByClassName('add-patient-button')[0];
            expect(btn.hasAttribute('disabled'));
        });
    });
});
