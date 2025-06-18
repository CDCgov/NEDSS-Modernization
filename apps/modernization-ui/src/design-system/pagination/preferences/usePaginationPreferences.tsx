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
    defaultPageSize?: ActivePagination;
};

const PaginationPreferenceProvider = ({ id, children, defaultPageSize }: Props) => {
    const [state, dispatch] = useReducer(reducer, {});

    const { value, save, remove } = useLocalStorage<ActivePagination>({ key: id, initial: defaultPageSize });

    useEffect(() => {
        if (value) {
            //  use the stored pagination value
            dispatch({ type: 'load', active: value });
        } else if (defaultPageSize) {
            dispatch({ type: 'load', active: defaultPageSize });
        }
    }, [value]);

    useEffect(() => {
        if (state.changed) {
            //  update the stored pagination when active is changed
            if (state.active) {
                save(state.active);
            } else {
                remove();
            }
        }
    }, [state.changed, state.active?.pageSize]);

    const { resize, reset, page } = usePagination();

    useEffect(() => {
        dispatch({ type: 'sync', pageSize: page.pageSize });
    }, [page.pageSize]);

    useEffect(() => {
        if (state.active) {
            resize(state.active.pageSize);
        } else {
            reset();
        }
    }, [state.active]);

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
