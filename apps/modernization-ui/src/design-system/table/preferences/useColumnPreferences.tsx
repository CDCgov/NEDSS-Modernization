import { ReactNode, createContext, useContext, useReducer, useState } from 'react';

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
    searchType: SearchType;
    preferences: ColumnPreference[];
    register: (searchType: SearchType, preferences: ColumnPreference[]) => void;
    save: (preferences: ColumnPreference[], searchType: SearchType) => void;
    reset: () => void;
    apply: <C extends HasPreference>(colums: C[]) => C[];
};

const ColumnPreferenceContext = createContext<Interaction | undefined>(undefined);

type State = {
    initial: ColumnPreference[];
    preferences: ColumnPreference[];
};

const initialize = (initial: ColumnPreference[]): State => ({ initial, preferences: initial });

type SearchType = 'Patients' | 'LabReports' | 'Investigations';

type Action =
    | { type: 'register'; preferences: ColumnPreference[] }
    | { type: 'save'; preferences: ColumnPreference[] }
    | { type: 'reset' };

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'register': {
            return { initial: action.preferences, preferences: action.preferences };
        }
        case 'save': {
            console.log('saving', current);
            return { ...current, preferences: action.preferences };
        }
        case 'reset': {
            return { ...current, preferences: current.initial };
        }
        default:
            return current;
    }
};

type Props = {
    children: ReactNode;
};

const ColumnPreferenceProvider = ({ children }: Props) => {
    const [state, dispatch] = useReducer(reducer, [], initialize);
    const [searchType, setSearchType] = useState<SearchType>('Patients');

    const register = (search: SearchType, preferences: ColumnPreference[]) => {
        setSearchType(search);
        dispatch({ type: 'register', preferences });
    };

    const save = (preferences: ColumnPreference[], search: SearchType) => {
        setSearchType(search);
        dispatch({ type: 'save', preferences });
    };

    const reset = () => dispatch({ type: 'reset' });
    const apply = applyPreferences(state.preferences);

    const value = {
        searchType,
        preferences: state.preferences,
        register,
        save,
        reset,
        apply
    };

    return <ColumnPreferenceContext.Provider value={value}>{children}</ColumnPreferenceContext.Provider>;
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
