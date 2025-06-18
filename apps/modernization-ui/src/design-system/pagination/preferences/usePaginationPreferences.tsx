import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { useLocalStorage } from 'storage';
import { usePagination } from 'pagination';

type ActivePagination = {
    pageSize: number;
};

type Interaction = {
    active?: ActivePagination;
    resizeOn: (selectable?: ActivePagination) => void;
};

const PaginationPreferencesContext = createContext<Interaction | undefined>(undefined);

type State = {
    changed?: Date;
    active?: ActivePagination;
};

type Action =
    | { type: 'paginateSize'; active: ActivePagination }
    | { type: 'load'; active: ActivePagination }
    | { type: 'sync'; pageSize?: number }
    | { type: 'reset' };

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
        case 'paginateSize': {
            return { changed: new Date(), active: { ...action.active } };
        }
        case 'reset': {
            return { changed: new Date() };
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

    const { value, save } = useLocalStorage<ActivePagination>({ key: id });

    const { resize, page } = usePagination();

    useEffect(() => {
        dispatch({ type: 'sync', pageSize: page.pageSize });
    }, [page.pageSize]);

    useEffect(() => {
        if (value) {
            //  use the stored pagination value
            dispatch({ type: 'load', active: value });
        }
    }, [value]);

    useEffect(() => {
        if (state.active?.pageSize) {
            save(state.active);
        }
    }, [state.active?.pageSize]);

    useEffect(() => {
        if (state.active?.pageSize) {
            resize(state.active.pageSize);
        }
    }, [state.active?.pageSize]);

    const resizeOn = (selectable?: ActivePagination) =>
        selectable
            ? dispatch({ type: 'paginateSize', active: { pageSize: selectable.pageSize } })
            : dispatch({ type: 'reset' });

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
export type { ActivePagination };
