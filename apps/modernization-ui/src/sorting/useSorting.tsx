import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Direction } from './Sort';

const SORT_ON_PARAMETER = 'sortOn';
const DIRECTION_PARAMETER = 'direction';

type SortingState = {
    sorting: Sorting;
    reset: () => void;
    sortBy: (property: string, direction: Direction) => void;
};

const SortingContext = createContext<SortingState | undefined>(undefined);

type Sorting = string | undefined;

type Action = { type: 'reset' } | { type: 'sort'; property: string; direction: Direction };

const reducer = (current: Sorting, action: Action): Sorting => {
    switch (action.type) {
        case 'reset':
            return undefined;
        case 'sort': {
            return action.direction === Direction.None ? undefined : asSort(action.property, action.direction);
        }
        default:
            return current;
    }
};

const asSort = (property: string, direction: Direction) => {
    if (direction === Direction.None) {
        return undefined;
    } else {
        return `${property},${fromDirection(direction)}`;
    }
};

const fromDirection = (direction: Direction) => {
    switch (direction) {
        case Direction.Ascending:
            return 'asc';
        case Direction.Descending:
            return 'desc';
        default:
            return undefined;
    }
};

const toDirection = (value: string) => {
    switch (value.toLowerCase()) {
        case 'asc':
            return Direction.Ascending;
        case 'desc':
            return Direction.Descending;
        default:
            return Direction.None;
    }
};

type SortingProviderProps = {
    children: ReactNode;
    appendToUrl?: boolean;
};

const SortingProvider = ({ appendToUrl = false, children }: SortingProviderProps) => {
    const [state, dispatch] = useReducer(reducer, undefined);

    const [searchParams, setSearchParams] = useSearchParams();

    const sortOn = searchParams.get(SORT_ON_PARAMETER);
    const direction = searchParams.get(DIRECTION_PARAMETER);

    useEffect(() => {
        if (appendToUrl && sortOn && direction) {
            dispatch({ type: 'sort', property: sortOn, direction: toDirection(direction) });
        } else if (appendToUrl) {
            dispatch({ type: 'reset' });
        }
    }, [appendToUrl, sortOn, direction]);

    const sortByParameter = (property: string, direction: Direction) => {
        if (direction === Direction.None) {
            setSearchParams((current) => {
                current.delete(SORT_ON_PARAMETER);
                current.delete(DIRECTION_PARAMETER);
                return current;
            });
        } else {
            setSearchParams((current) => {
                current.set(SORT_ON_PARAMETER, property);
                current.set(DIRECTION_PARAMETER, direction);
                return current;
            });
        }
    };

    const sortByDispatch = (property: string, direction: Direction) => dispatch({ type: 'sort', property, direction });

    const value = {
        sorting: state,
        reset: () => dispatch({ type: 'reset' }),
        sortBy: appendToUrl ? sortByParameter : sortByDispatch
    };

    return <SortingContext.Provider value={value}>{children}</SortingContext.Provider>;
};

const useSorting = () => {
    const context = useContext(SortingContext);

    if (context === undefined) {
        throw new Error('useSorting must be used within a SortingProvider');
    }

    return context;
};

export type { Sorting };

export { SortingProvider, useSorting };
