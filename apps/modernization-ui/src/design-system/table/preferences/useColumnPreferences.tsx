import { ReactNode, createContext, useContext, useEffect, useMemo, useReducer } from 'react';
import { useLocalStorage } from 'storage';

type ColumnPreference = {
    id: string;
    name: string;
    moveable?: boolean;
    toggleable?: boolean;
    hidden?: boolean;
};

type HasPreference = {
    id: string;
};

type Interaction = {
    preferences: ColumnPreference[];
    save: (preferences: ColumnPreference[]) => void;
    reset: () => void;
    apply: <C extends HasPreference>(colums: C[]) => C[];
};

const ColumnPreferenceContext = createContext<Interaction | undefined>(undefined);

type State = {
    changed?: Date;
    initial: ColumnPreference[];
    preferences: ColumnPreference[];
};

const initialize = (initial: ColumnPreference[]): State => step(initial, initial);

const step = (initial: ColumnPreference[], next: ColumnPreference[]): State => ({
    initial: structuredClone(initial),
    preferences: structuredClone(next)
});

type Action =
    | { type: 'save'; preferences: ColumnPreference[] }
    | { type: 'reset' }
    | { type: 'load'; preferences: ColumnPreference[] };

const reducer = (state: State, action: Action): State => {
    switch (action.type) {
        case 'save':
            return { changed: new Date(), ...step(state.initial, action.preferences) };

        case 'reset':
            return { changed: new Date(), ...initialize(state.initial) };

        case 'load': {
            return { ...state, ...step(state.initial, action.preferences) };
        }

        default:
            return state;
    }
};

type Props = {
    id: string;
    children: ReactNode;
    defaults?: ColumnPreference[];
};

const ColumnPreferenceProvider = ({ id, children, defaults = [] }: Props) => {
    const [state, dispatch] = useReducer(reducer, defaults, initialize);

    const { value, save } = useLocalStorage({ key: id, initial: defaults });

    useEffect(() => {
        if (value) {
            dispatch({ type: 'load', preferences: value });
        }
    }, [value]);

    useEffect(() => {
        if (state.changed) {
            save(state.preferences);
        }
    }, [state.changed]);

    const interaction = useMemo(
        () => ({
            preferences: state.preferences,
            save: (preferences: ColumnPreference[]) => dispatch({ type: 'save', preferences }),
            reset: () => dispatch({ type: 'reset' }),
            apply: applyPreferences(state.preferences)
        }),
        [dispatch, state.preferences]
    );

    return <ColumnPreferenceContext.Provider value={interaction}>{children}</ColumnPreferenceContext.Provider>;
};

const useColumnPreferences = () => {
    const context = useContext(ColumnPreferenceContext);

    if (context === undefined) {
        throw new Error('useColumnPreferences must be used within a SearchResultDisplayProvider');
    }

    return context;
};

const applyPreferences =
    (preferences: ColumnPreference[]) =>
    <C extends HasPreference>(columns: C[]): C[] => {
        return preferences.reduce((previous, preference) => {
            if (!preference.hidden) {
                const column = columns.find((c) => c.id === preference.id);

                if (column) {
                    return [...previous, column];
                }
            }

            return previous;
        }, [] as C[]);
    };

export { useColumnPreferences, ColumnPreferenceProvider };
export type { ColumnPreference };
