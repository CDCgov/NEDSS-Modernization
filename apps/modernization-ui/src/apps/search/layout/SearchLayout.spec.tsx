import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { SearchLayout } from './SearchLayout';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { SearchResultDisplayProvider } from '../useSearchResultDisplay';
import { FilterProvider } from 'design-system/filter/useFilter';
import userEvent from '@testing-library/user-event';
import { ReactNode } from 'react';

vi.mock('pagination', () => ({
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

vi.mock('libs/sorting', () => ({
    useSorting: () => ({
        reset: jest.fn(),
        sortBy: jest.fn(),
        toggle: jest.fn()
    })
}));

vi.mock('apps/search', () => ({
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

type FixtureProps = {
    criteria?: () => ReactNode;
    searchEnabled?: boolean;
    onSearch?: () => void;
};

const Fixture = ({ criteria, searchEnabled, onSearch }: FixtureProps) => (
    <MemoryRouter>
        <SkipLinkProvider>
            <SearchResultDisplayProvider>
                <FilterProvider>
                    <SearchLayout
                        criteria={criteria ?? jest.fn()}
                        resultsAsList={jest.fn()}
                        resultsAsTable={jest.fn()}
                        onSearch={onSearch ?? jest.fn()}
                        onClear={jest.fn()}
                        searchEnabled={searchEnabled}
                    />
                </FilterProvider>
            </SearchResultDisplayProvider>
        </SkipLinkProvider>
    </MemoryRouter>
);

describe('SearchLayout', () => {
    it('should render no input by default', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText(/You must enter at least one item to search/)).toBeInTheDocument();
    });

    it('calls onSearch when Enter is pressed on an input and searchEnabled is true', async () => {
        const user = userEvent.setup();
        const onSearch = jest.fn();
        const { getByRole } = render(
            <Fixture criteria={() => <input type="text" />} searchEnabled={true} onSearch={onSearch} />
        );

        const input = getByRole('textbox');
        input.focus();
        await user.keyboard('{Enter}');
        expect(onSearch).toHaveBeenCalled();
    });

    it('calls onSearch when Enter is pressed on an select and searchEnabled is true', async () => {
        const user = userEvent.setup();
        const onSearch = jest.fn();
        const { getByRole } = render(<Fixture criteria={() => <select />} searchEnabled={true} onSearch={onSearch} />);
        const select = getByRole('combobox');
        select.focus();
        await user.keyboard('{Enter}');
        expect(onSearch).toHaveBeenCalled();
    });

    it('does not call onSearch when Enter is pressed on a button and searchEnabled is false', async () => {
        const user = userEvent.setup();
        const onSearch = jest.fn();
        const { getByRole } = render(
            <Fixture criteria={() => <input type="text" />} searchEnabled={false} onSearch={onSearch} />
        );
        const button = getByRole('button', { name: 'Search' });
        button.focus();
        await user.keyboard('{Enter}');
        expect(onSearch).not.toHaveBeenCalled();
    });
});
