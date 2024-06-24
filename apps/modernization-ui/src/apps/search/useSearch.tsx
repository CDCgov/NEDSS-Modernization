import { ReactNode, createContext, useContext, useReducer } from 'react';
import { PageProvider, PagingSettings } from 'page';
import { SortingProvider, SortingSettings } from 'sorting';

type View = 'list' | 'table';

type Term = {
    source: string;
    name: string;
    value: string | number;
};

type Results = {
    terms: Term[];
    total: number;
};

type Waiting = { status: 'waiting' };

type Searching = { status: 'searching' };

type Complete = { status: 'completed'; results: Results };

type SearchState = { view: View } & (Waiting | Searching | Complete);

type SearchInteraction = {
    status: 'waiting' | 'searching' | 'completed';
    view: View;
    results: Results;
    reset: () => void;
    search: () => void;
    complete: (terms: Term[], total: number) => void;
};

const SearchContext = createContext<SearchInteraction | undefined>(undefined);

type Action = { type: 'reset' } | { type: 'search' } | { type: 'complete'; terms: Term[]; total: number };

const initial: SearchState = { status: 'waiting', view: 'list' };

const emptyResults = { total: 0, terms: [] };

const reducer = (current: SearchState, action: Action): SearchState => {
    switch (action.type) {
        case 'reset': {
            return initial;
        }
        case 'search': {
            return { ...current, status: 'searching' };
        }
        case 'complete': {
            return { ...current, status: 'completed', results: { terms: action.terms, total: action.total } };
        }
        default:
            return current;
    }
};

type Props = {
    children: ReactNode;
    sorting?: SortingSettings;
    paging?: PagingSettings;
};

const SearchProvider = ({ sorting, paging, children }: Props) => {
    return (
        <SortingProvider {...sorting} appendToUrl={sorting?.appendToUrl === undefined ? false : sorting.appendToUrl}>
            <PageProvider {...paging} appendToUrl={paging?.appendToUrl === undefined ? false : paging.appendToUrl}>
                <InternalSearchProvider>{children}</InternalSearchProvider>
            </PageProvider>
        </SortingProvider>
    );
};

const InternalSearchProvider = ({ children }: { children: ReactNode }) => {
    const [state, dispatch] = useReducer(reducer, initial);

    const complete = (terms: Term[], total: number) => dispatch({ type: 'complete', terms, total });

    const reset = () => dispatch({ type: 'reset' });
    const search = () => dispatch({ type: 'search' });
    const results = state.status === 'completed' ? state.results : emptyResults;

    const value = {
        status: state.status,
        view: state.view,
        results,
        reset,
        search,
        complete
    };

    return <SearchContext.Provider value={value}>{children}</SearchContext.Provider>;
};

const useSearch = () => {
    const context = useContext(SearchContext);

    if (context === undefined) {
        throw new Error('useSearch must be used within a SearchProvider');
    }

    return context;
};

export type { Term, Results, View };

export { SearchProvider, useSearch };
