import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { Direction, useSorting } from 'sorting';
import { useLocalStorage } from 'storage';
import { SortingSelectable } from './selectable';

type ActiveSorting = {
    property: string;
    direction: Direction;
};

type Interaction = {
    available: SortingSelectable[];
    active?: ActiveSorting;
    sortOn: (selectable?: ActiveSorting) => void;
};

const SortingPreferencesContext = createContext<Interaction | undefined>(undefined);

type State = {
    status: 'sorted' | 'unsorted';
    changed?: Date;
    active?: ActiveSorting;
};

type Action =
    | { type: 'sort'; active: ActiveSorting }
    | { type: 'load'; active: ActiveSorting }
    | { type: 'sync'; property?: string; direction?: Direction }
    | { type: 'reset' };

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'sync': {
            if (current.active?.property !== action.property && current.active?.direction !== action.direction) {
                //  the active sorting differs from the preference
                return action.property && action.direction
                    ? //  update the preference to match the active sorting
                      {
                          changed: new Date(),
                          status: 'sorted',
                          active: { property: action.property, direction: action.direction }
                      }
                    : //  reset the preference to match the active sorting
                      { changed: new Date(), status: 'unsorted' };
            }
            return current;
        }
        case 'sort': {
            return { changed: new Date(), status: 'sorted', active: { ...action.active } };
        }
        case 'reset': {
            return { changed: new Date(), status: 'unsorted' };
        }
        case 'load': {
            return { ...current, status: 'sorted', active: { ...action.active } };
        }
        default:
            return current;
    }
};

type Props = {
    id: string;
    children: ReactNode;
    available?: SortingSelectable[];
    defaultSort?: ActiveSorting;
};

const SortingPreferenceProvider = ({ id, children, available = [], defaultSort }: Props) => {
    const [state, dispatch] = useReducer(reducer, { status: 'unsorted' });

    const { value, save, remove } = useLocalStorage<ActiveSorting>({ key: id });

    useEffect(() => {
        if (value) {
            //  use the stored sorting
            dispatch({ type: 'load', active: value });
        } else if (defaultSort) {
            dispatch({ type: 'load', active: defaultSort });
        }
    }, [value]);

    useEffect(() => {
        if (state.changed) {
            //  update the stored sorting when active is changed
            if (state.active) {
                save(state.active);
            } else {
                remove();
            }
        }
    }, [state.changed, state.active?.property, state.active?.direction]);

    const { sortBy, reset, property, direction } = useSorting();

    useEffect(() => {
        dispatch({ type: 'sync', property, direction });
    }, [property, direction]);

    useEffect(() => {
        if (state.status === 'sorted' && state.active) {
            sortBy(state.active.property, state.active.direction);
        } else if (state.status === 'unsorted') {
            reset();
        }
    }, [state.active, state.status]);

    const sortOn = (selectable?: ActiveSorting) =>
        selectable
            ? dispatch({ type: 'sort', active: { property: selectable.property, direction: selectable.direction } })
            : dispatch({ type: 'reset' });

    const interaction = {
        available,
        active: state.active,
        sortOn
    };

    return <SortingPreferencesContext.Provider value={interaction}>{children}</SortingPreferencesContext.Provider>;
};

const useSortingPreferences = () => {
    const context = useContext(SortingPreferencesContext);

    if (context === undefined) {
        throw new Error('useSortingPreferences must be used within a SortingPreferenceProvider');
    }

    return context;
};

export { useSortingPreferences, SortingPreferenceProvider };
export type { ActiveSorting };
