import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { useLocalStorage } from 'storage';

type PaginationPreference = {
    resultsPerPage: number;
};

type Interaction = {
    available: number[];
    paginationSettings?: PaginationPreference;
    setResultsPerPage: (resultsPerPage: number) => void;
};

const PaginationPreferencesContext = createContext<Interaction | undefined>(undefined);

type State = {
    status: 'set' | 'unset';
    paginationSettings?: PaginationPreference;
};

type Action =
    | { type: 'set'; resultsPerPage: number }
    | { type: 'load'; paginationSettings: PaginationPreference }
    | { type: 'sync'; resultsPerPage?: number }
    | { type: 'reset' };

const DEFAULT_RESULTS_PER_PAGE = 20;
const DEFAULT_AVAILABLE_OPTIONS = [20, 30, 50, 100];

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'sync': {
            if (current.paginationSettings?.resultsPerPage === action.resultsPerPage) {
                return current;
            }
            return action.resultsPerPage
                ? { status: 'set', paginationSettings: { resultsPerPage: action.resultsPerPage } }
                : { status: 'unset' };
        }
        case 'set': {
            return { status: 'set', paginationSettings: { resultsPerPage: action.resultsPerPage } };
        }
        case 'reset': {
            return { status: 'unset' };
        }
        case 'load': {
            return { ...current, status: 'set', paginationSettings: { ...action.paginationSettings } };
        }
    }
};

type Props = {
    id: string;
    children: ReactNode;
    available?: number[];
    defaultResultsPerPage?: number;
};

const PaginationPreferenceProvider = ({
    id,
    children,
    available = DEFAULT_AVAILABLE_OPTIONS,
    defaultResultsPerPage = DEFAULT_RESULTS_PER_PAGE
}: Props) => {
    const [state, dispatch] = useReducer(reducer, { status: 'unset' });

    const { value, save, remove } = useLocalStorage<PaginationPreference>({
        key: id,
        initial: defaultResultsPerPage ? { resultsPerPage: defaultResultsPerPage } : undefined
    });

    useEffect(() => {
        if (value) {
            dispatch({ type: 'load', paginationSettings: value });
        } else if (defaultResultsPerPage) {
            dispatch({ type: 'load', paginationSettings: { resultsPerPage: defaultResultsPerPage } });
        }
    }, [value, defaultResultsPerPage]);

    useEffect(() => {
        if (state.paginationSettings) {
            save(state.paginationSettings);
        } else {
            remove();
        }
    }, [state.paginationSettings?.resultsPerPage]);

    const setResultsPerPage = (resultsPerPage: number) => dispatch({ type: 'set', resultsPerPage });

    return (
        <PaginationPreferencesContext.Provider
            value={{
                available,
                paginationSettings: state.paginationSettings,
                setResultsPerPage
            }}>
            {children}
        </PaginationPreferencesContext.Provider>
    );
};

const usePaginationPreferences = () => {
    const context = useContext(PaginationPreferencesContext);

    if (context === undefined) {
        throw new Error('usePaginationPreferences must be used within a PaginationPreferenceProvider');
    }

    return context;
};

export { usePaginationPreferences, PaginationPreferenceProvider };
export type { PaginationPreference };
