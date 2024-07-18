import { ReactNode, createContext, useContext, useReducer } from 'react';
import { PageProvider, PagingSettings } from 'page';
import { SortingProvider, SortingSettings } from 'sorting';
import { Term } from './terms';

type View = 'list' | 'table';

type Waiting = { status: 'waiting' };

type Searching = { status: 'searching' };

type Complete = { status: 'completed'; terms: Term[] };

type NoInput = { status: 'noInput' };

type State = { view: View } & (Waiting | Searching | Complete | NoInput);

type Interaction = {
    status: 'waiting' | 'searching' | 'completed' | 'noInput';
    view: View;
    terms: Term[];
    reset: () => void;
    search: () => void;
    complete: (terms: Term[]) => void;
    noInput: () => void;
    setView: (view: View) => void;
};

const SearchContext = createContext<Interaction | undefined>(undefined);

type Action =
    | { type: 'reset' }
    | { type: 'search' }
    | { type: 'complete'; terms: Term[] }
    | { type: 'setView'; view: View }
    | { type: 'noInput' };

const initial: State = {
    status: 'waiting',
    view: 'table'
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
        case 'noInput': {
            return { ...current, status: 'noInput' };
        }
        case 'setView': {
            return { ...current, view: action.view };
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

    const noInput = () => dispatch({ type: 'noInput' });

    const reset = () => dispatch({ type: 'reset' });
    const search = () => dispatch({ type: 'search' });
    const terms = state.status === 'completed' ? state.terms : [];
    const setView = (view: View) => dispatch({ type: 'setView', view });

    const value = {
        status: state.status,
        view: state.view,
        terms,
        reset,
        search,
        complete,
        noInput,
        setView
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
