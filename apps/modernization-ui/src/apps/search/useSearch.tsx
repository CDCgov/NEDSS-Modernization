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
    console.log(action);
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
        <SortingProvider {...sorting} appendToUrl={sorting?.appendToUrl === undefined ? true : sorting.appendToUrl}>
            <PageProvider {...paging} appendToUrl={paging?.appendToUrl === undefined ? true : paging.appendToUrl}>
                <InternalSearchProvider>{children}</InternalSearchProvider>
            </PageProvider>
        </SortingProvider>
    );
};

const InternalSearchProvider = ({ children }: { children: ReactNode }) => {
    const [state, dispatch] = useReducer(reducer, initial);

    const value = {
        status: state.status,
        view: state.view,
        results: state.status === 'completed' ? state.results : emptyResults,
        reset: () => dispatch({ type: 'reset' }),
        search: () => dispatch({ type: 'search' }),
        complete: (terms: Term[], total: number) => dispatch({ type: 'complete', terms, total })
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
