import { ReactNode, createContext, useContext, useReducer } from 'react';
import { PageProvider, PagingSettings } from 'page';
import { SortingProvider, SortingSettings } from 'sorting';
import { Term } from './terms';

type View = 'list' | 'table';

export enum SortDirection {
    Asc = 'ASC',
    Desc = 'DESC'
}

export enum SortField {
    BirthTime = 'birthTime',
    LastNm = 'lastNm',
    Relevance = 'relevance'
}

type Sort = { sortDirection?: SortDirection; sortField: SortField };

type Waiting = { status: 'waiting' };

type Searching = { status: 'searching' };

type Complete = { status: 'completed'; terms: Term[] };

type State = { view: View; sort: Sort } & (Waiting | Searching | Complete);

type Interaction = {
    status: 'waiting' | 'searching' | 'completed';
    view: View;
    terms: Term[];
    sort: Sort;
    reset: () => void;
    search: () => void;
    complete: (terms: Term[]) => void;
    setView: (view: View) => void;
    setSort: (sort: Sort) => void;
};

const SearchContext = createContext<Interaction | undefined>(undefined);

type Action =
    | { type: 'reset' }
    | { type: 'search' }
    | { type: 'complete'; terms: Term[] }
    | { type: 'setView'; view: View }
    | { type: 'setSort'; view: Sort };

const initial: State = {
    status: 'waiting',
    view: 'list',
    sort: {
        sortField: SortField.Relevance
    }
};

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'reset': {
            return initial;
        }
        case 'search': {
            return { ...current, status: 'searching' };
        }
        case 'complete': {
            return { ...current, status: 'completed', terms: action.terms };
        }
        case 'setView': {
            return { ...current, view: action.view };
        }
        case 'setSort': {
            return { ...current, sort: action.view };
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

const SearchResultDisplayProvider = ({ sorting, paging, children }: Props) => {
    return (
        <SortingProvider {...sorting} appendToUrl={sorting?.appendToUrl === undefined ? false : sorting.appendToUrl}>
            <PageProvider {...paging} appendToUrl={paging?.appendToUrl === undefined ? false : paging.appendToUrl}>
                <Wrapper>{children}</Wrapper>
            </PageProvider>
        </SortingProvider>
    );
};

const Wrapper = ({ children }: { children: ReactNode }) => {
    const [state, dispatch] = useReducer(reducer, initial);

    const complete = (terms: Term[]) => dispatch({ type: 'complete', terms });

    const reset = () => dispatch({ type: 'reset' });
    const search = () => dispatch({ type: 'search' });
    const terms = state.status === 'completed' ? state.terms : [];
    const setView = (view: View) => dispatch({ type: 'setView', view });
    const setSort = (sort: Sort) => dispatch({ type: 'setSort', view: sort });

    const value = {
        status: state.status,
        view: state.view,
        sort: state.sort,
        terms,
        reset,
        search,
        complete,
        setView,
        setSort
    };

    return <SearchContext.Provider value={value}>{children}</SearchContext.Provider>;
};

const useSearchResultDisplay = () => {
    const context = useContext(SearchContext);

    if (context === undefined) {
        throw new Error('useSearchResultDisplay must be used within a SearchResultDisplayProvider');
    }

    return context;
};

export type { Term, View };

export { SearchResultDisplayProvider, useSearchResultDisplay };
