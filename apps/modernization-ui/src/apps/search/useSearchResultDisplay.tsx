import { ReactNode, createContext, useCallback, useContext, useEffect, useMemo, useReducer } from 'react';
import { PageProvider, PagingSettings } from 'page';
import { SortingProvider, SortingSettings } from 'sorting';
import { Term } from './terms';
import { SearchSettings, useSearchSettings } from './useSearchSettings';

type View = 'list' | 'table';

type Waiting = { status: 'waiting' };

type Searching = { status: 'searching' };

type Complete = { status: 'completed'; terms: Term[] };

type NoInput = { status: 'no-input' };

type State = { view: View } & (Waiting | Searching | Complete | NoInput);

type Interaction = {
    status: 'waiting' | 'searching' | 'completed' | 'no-input';
    view: View;
    terms: Term[];
    reset: () => void;
    search: () => void;
    complete: (terms: Term[]) => void;
    noInput: () => void;
    asTable: () => void;
    asList: () => void;
};

const SearchContext = createContext<Interaction | undefined>(undefined);

type Action =
    | { type: 'reset' }
    | { type: 'search' }
    | { type: 'complete'; terms: Term[] }
    | { type: 'setView'; view: View }
    | { type: 'no-input' };

const initialize = (settings: SearchSettings): State => ({ status: 'waiting', view: settings.defaultView });

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'reset': {
            return { view: current.view, status: 'waiting' };
        }
        case 'search': {
            return { ...current, status: 'searching' };
        }
        case 'complete': {
            return { ...current, status: 'completed', terms: action.terms };
        }
        case 'no-input': {
            return { ...current, status: 'no-input' };
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
    const { settings, updateDefaultView } = useSearchSettings();

    return (
        <SortingProvider {...sorting} appendToUrl={sorting?.appendToUrl === undefined ? false : sorting.appendToUrl}>
            <PageProvider {...paging} appendToUrl={paging?.appendToUrl === undefined ? false : paging.appendToUrl}>
                <Wrapper settings={settings} updateDefaultView={updateDefaultView}>
                    {children}
                </Wrapper>
            </PageProvider>
        </SortingProvider>
    );
};

type WrapperProps = {
    children: ReactNode;
    settings: SearchSettings;
    updateDefaultView: (newView: View) => void;
};

const Wrapper = ({ children, settings, updateDefaultView }: WrapperProps) => {
    const [state, dispatch] = useReducer(reducer, settings, initialize);

    useEffect(() => {
        //  udpates the current view if the default changes
        if (settings.defaultView === 'list') {
            dispatch({ type: 'setView', view: 'list' });
        } else if (settings.defaultView === 'table') {
            dispatch({ type: 'setView', view: 'table' });
        }
    }, [settings.defaultView]);

    const terms = useMemo(() => (state.status === 'completed' ? state.terms : []), [state.status]);

    const complete = useCallback((terms: Term[]) => dispatch({ type: 'complete', terms }), [dispatch]);
    const noInput = useCallback(() => dispatch({ type: 'no-input' }), [dispatch]);
    const reset = useCallback(() => dispatch({ type: 'reset' }), [dispatch]);
    const search = useCallback(() => dispatch({ type: 'search' }), [dispatch]);

    const asTable = () => {
        updateDefaultView('table');
        dispatch({ type: 'setView', view: 'table' });
    };
    const asList = () => {
        updateDefaultView('list');
        dispatch({ type: 'setView', view: 'list' });
    };

    const value = {
        status: state.status,
        view: state.view,
        terms,
        reset,
        search,
        complete,
        noInput,
        asTable,
        asList
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
