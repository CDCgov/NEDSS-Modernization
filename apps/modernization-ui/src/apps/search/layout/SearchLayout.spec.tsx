import { render, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { SearchLayout } from './SearchLayout';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { SearchPageProvider } from '../SearchPage';

jest.mock('apps/search', () => ({
    useSearchResultDisplay: () => ({
        view: 'list'
    }),
    useSearchInteraction: () => ({ status: 'no-input', results: { total: 199, terms: [] } })
}));

describe('no input', () => {
    it('should render no input', async () => {
        const { container } = render(
            <MemoryRouter>
                <SkipLinkProvider>
                    <SearchPageProvider>
                        <SearchLayout
                            criteria={jest.fn()}
                            resultsAsList={jest.fn()}
                            resultsAsTable={jest.fn()}
                            onSearch={jest.fn()}
                            onClear={jest.fn()}
                        />
                    </SearchPageProvider>
                </SkipLinkProvider>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(container).toHaveTextContent('You must enter at least one item to search');
        });
    });
});
