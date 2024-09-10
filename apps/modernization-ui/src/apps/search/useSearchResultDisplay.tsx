import { ReactNode, createContext, useCallback, useContext, useEffect, useReducer } from 'react';
import { SearchSettings, useSearchSettings } from './useSearchSettings';
import { useLocalStorage } from 'storage';

type View = 'list' | 'table';

type ViewPreference = {
    view: View;
};

type State = { preference: ViewPreference; changed?: Date };

type Interaction = {
    view: View;
    asTable: () => void;
    asList: () => void;
};

const SearchContext = createContext<Interaction | undefined>(undefined);

type Action = { type: 'load'; view: View } | { type: 'save'; view: View };

const initialize = (settings: SearchSettings): State => ({ preference: { view: settings.defaultView } });

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'save': {
            return { changed: new Date(), preference: { view: action.view } };
        }
        case 'load': {
            return { ...current, preference: { view: action.view } };
        }
        default:
            return current;
    }
};

type Props = {
    children: ReactNode;
};

const SearchResultDisplayProvider = ({ children }: Props) => {
    const { settings } = useSearchSettings();

    const [state, dispatch] = useReducer(reducer, settings, initialize);

    const { value, save } = useLocalStorage<ViewPreference>({
        key: 'search.view',
        initial: { view: settings.defaultView }
    });

    useEffect(() => {
        if (value) {
            dispatch({ type: 'load', ...value });
        }
    }, [value]);

    useEffect(() => {
        if (state.changed) {
            save(state.preference);
        }
    }, [state.changed]);

    const asTable = useCallback(() => dispatch({ type: 'save', view: 'table' }), [dispatch]);

    const asList = useCallback(() => dispatch({ type: 'save', view: 'list' }), [dispatch]);

    const interaction = {
        view: state.preference.view,
        asTable,
        asList
    };

    return <SearchContext.Provider value={interaction}>{children}</SearchContext.Provider>;
};

const useSearchResultDisplay = () => {
    const context = useContext(SearchContext);

    if (context === undefined) {
        throw new Error('useSearchResultDisplay must be used within a SearchResultDisplayProvider');
    }

    return context;
};

export type { View };

export { SearchResultDisplayProvider, useSearchResultDisplay };
