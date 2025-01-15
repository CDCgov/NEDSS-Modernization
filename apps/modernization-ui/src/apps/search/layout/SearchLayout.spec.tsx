import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { SearchLayout } from './SearchLayout';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { SearchResultDisplayProvider } from '../useSearchResultDisplay';
import { FilterPreferencesProvider } from 'design-system/sorting/preferences/useFilterPreferences';

jest.mock('page', () => ({
    usePage: () => ({
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

jest.mock('sorting', () => ({
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
    useFilterPreferences: () => ({
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
                        <FilterPreferencesProvider>
                            <SearchLayout
                                criteria={jest.fn()}
                                resultsAsList={jest.fn()}
                                resultsAsTable={jest.fn()}
                                onSearch={jest.fn()}
                                onClear={jest.fn()}
                            />
                        </FilterPreferencesProvider>
                    </SearchResultDisplayProvider>
                </SkipLinkProvider>
            </MemoryRouter>
        );

        expect(getByText(/You must enter at least one item to search/)).toBeInTheDocument();
    });
});
