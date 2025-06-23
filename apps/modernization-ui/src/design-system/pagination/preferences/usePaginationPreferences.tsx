import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { useLocalStorage } from 'storage';
import { usePagination } from 'pagination';

type PaginationSizePreference = {
    pageSize: number;
};

type Interaction = {
    paginationSize?: PaginationSizePreference;
    resizeOn: (selectedPaginationSize?: PaginationSizePreference) => void;
};

const PaginationPreferencesContext = createContext<Interaction | undefined>(undefined);

type State = {
    changed?: Date;
    active?: PaginationSizePreference;
};

type Action = { type: 'load'; active: PaginationSizePreference } | { type: 'sync'; pageSize?: number };

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'sync': {
            if (current.active?.pageSize !== action.pageSize) {
                //  the active pagination differs from the preference
                return action.pageSize
                    ? //  update the preference to match the active pagination
                      {
                          changed: new Date(),
                          active: { pageSize: action.pageSize }
                      }
                    : //  reset the preference to match the active pagination
                      { changed: new Date() };
            }
            return current;
        }

        case 'load': {
            return { ...current, active: { ...action.active } };
        }
        default:
            return current;
    }
};

type Props = {
    id: string;
    children: ReactNode;
};

const PaginationPreferenceProvider = ({ id, children }: Props) => {
    const [state, dispatch] = useReducer(reducer, { changed: new Date() });

    const { value, save } = useLocalStorage<PaginationSizePreference>({ key: id });

    const { resize, page } = usePagination();

    useEffect(() => {
        dispatch({ type: 'sync', pageSize: page.pageSize });
    }, [page.pageSize]);

    useEffect(() => {
        if (value) {
            dispatch({ type: 'load', active: value });
        }
    }, [value]);

    useEffect(() => {
        if (state.active?.pageSize) {
            save(state.active);
            resize(state.active.pageSize);
        }
    }, [state.active?.pageSize]);

    const resizeOn = () => dispatch({ type: 'sync' });

    const interaction = {
        active: state.active,
        resizeOn
    };

    return (
        <PaginationPreferencesContext.Provider value={interaction}>{children}</PaginationPreferencesContext.Provider>
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
export type { PaginationSizePreference };
