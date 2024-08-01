import { AdvancedSearch } from 'apps/search/advancedSearch/AdvancedSearch';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { MockedProvider } from '@apollo/client/testing';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { SearchResultDisplayProvider } from '../useSearchResultDisplay';

const mockedUsedNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate
}));

describe('AdvancedSearch component tests', () => {
    it('should render filter to do advance search', () => {});
    describe('When page loads', () => {
        it('Add New button is disabled', () => {
            const { getByText } = render(
                <MockedProvider>
                    <BrowserRouter>
                        <SkipLinkProvider>
                            <SearchResultDisplayProvider>
                                <AdvancedSearch />
                            </SearchResultDisplayProvider>
                        </SkipLinkProvider>
                    </BrowserRouter>
                </MockedProvider>
            );

            const btn = getByText('Add new');
            expect(btn.hasAttribute('disabled'));
        });
    });
});
