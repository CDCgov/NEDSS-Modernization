import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { SearchLayout } from './SearchLayout';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { SearchResultDisplayProvider } from '../useSearchResultDisplay';
import { FilterProvider } from 'design-system/filter/useFilter';

jest.mock('pagination', () => ({
    usePagination: () => ({
        page: {
            status: 0,
            pageSize: 1,
            total: 1,
            current: 1
        },
        firstPage: jest.fn(),
        reload: jest.fn(),
        request: jest.fn(),
        ready: jest.fn(),
        resize: jest.fn(),
        reset: jest.fn()
    })
}));

jest.mock('libs/sorting', () => ({
    useSorting: () => ({
        reset: jest.fn(),
        sortBy: jest.fn(),
        toggle: jest.fn()
    })
}));

jest.mock('apps/search', () => ({
    useSearchResultDisplay: () => ({
        view: 'list'
    }),
    useSearchInteraction: () => ({ status: 'no-input', results: { total: 1, terms: [] } }),
    useFilter: () => ({
        filterable: false,
        toggleFilterable: jest.fn(),
        applyFilter: jest.fn()
    })
}));

describe('when a search is performed without ', () => {
    it('should render no input', () => {
        const { getByText } = render(
            <MemoryRouter>
                <SkipLinkProvider>
                    <SearchResultDisplayProvider>
                        <FilterProvider>
                            <SearchLayout
                                criteria={jest.fn()}
                                resultsAsList={jest.fn()}
                                resultsAsTable={jest.fn()}
                                onSearch={jest.fn()}
                                onClear={jest.fn()}
                            />
                        </FilterProvider>
                    </SearchResultDisplayProvider>
                </SkipLinkProvider>
            </MemoryRouter>
        );

        expect(getByText(/You must enter at least one item to search/)).toBeInTheDocument();
    });
});
