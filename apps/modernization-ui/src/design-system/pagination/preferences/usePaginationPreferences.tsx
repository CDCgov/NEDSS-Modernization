import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { useLocalStorage } from 'storage';

type Interaction = {
    paginationSettings?: { resultsPerPage: number };
    setResultsPerPage: (resultsPerPage: number) => void;
};

const PaginationPreferencesContext = createContext<Interaction | undefined>(undefined);

type State = {
    status: 'set' | 'unset';
    paginationSettings?: { resultsPerPage: number };
};

type Action =
    | { type: 'set'; resultsPerPage: number }
    | { type: 'load'; resultsPerPage: number }
    | { type: 'sync'; resultsPerPage?: number }
    | { type: 'reset' };

const DEFAULT_RESULTS_PER_PAGE = 20;

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
            return { ...current, status: 'set', paginationSettings: { resultsPerPage: action.resultsPerPage } };
        }
    }
};

type Props = {
    id: string;
    children: ReactNode;
    defaultResultsPerPage?: number;
};

const PaginationPreferenceProvider = ({ id, children, defaultResultsPerPage = DEFAULT_RESULTS_PER_PAGE }: Props) => {
    const [state, dispatch] = useReducer(reducer, { status: 'unset' });

    const { value, save, remove } = useLocalStorage<{ resultsPerPage: number }>({
        key: id,
        initial: defaultResultsPerPage ? { resultsPerPage: defaultResultsPerPage } : undefined
    });

    useEffect(() => {
        if (value) {
            dispatch({ type: 'load', resultsPerPage: value.resultsPerPage });
        } else if (defaultResultsPerPage) {
            dispatch({ type: 'load', resultsPerPage: defaultResultsPerPage });
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
