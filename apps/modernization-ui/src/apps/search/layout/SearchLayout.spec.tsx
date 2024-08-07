import { render, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { SearchLayout } from './SearchLayout';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { SearchResultDisplayProvider } from 'apps/search/useSearchResultDisplay';

const values = {
    status: 'noInput',
    view: 'list',
    terms: []
};

jest.mock('apps/search', () => ({
    useSearchResultDisplay: () => values
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
