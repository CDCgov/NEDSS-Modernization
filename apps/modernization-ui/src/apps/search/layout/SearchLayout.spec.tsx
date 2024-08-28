import { render, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { SearchLayout } from './SearchLayout';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { SearchResultDisplayProvider } from 'apps/search/useSearchResultDisplay';

const mockReset = jest.fn();

jest.mock('apps/search', () => ({
    useSearchResultDisplay: () => ({
        status: 'no-input',
        view: 'list',
        terms: [],
        reset: mockReset
    })
}));

describe('no input', () => {
    it('should render no input', async () => {
        const { container } = render(
            <MemoryRouter>
                <SkipLinkProvider>
                    <SearchResultDisplayProvider>
                        <SearchLayout
                            criteria={jest.fn()}
                            resultsAsList={jest.fn()}
                            resultsAsTable={jest.fn()}
                            onSearch={jest.fn()}
                            onClear={jest.fn()}
                            onRemoveTerm={jest.fn()}
                        />
                    </SearchResultDisplayProvider>
                </SkipLinkProvider>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(container).toHaveTextContent('You must enter at least one item to search');
        });
    });
});
