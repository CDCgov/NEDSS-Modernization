import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Direction } from './Sort';

const SORT_ON_PARAMETER = 'sortOn';
const DIRECTION_PARAMETER = 'direction';

type Interaction = {
    property?: string;
    direction?: Direction;
    sorting?: string;
    reset: () => void;
    sortBy: (property: string, direction: Direction) => void;
    toggle: (property: string) => void;
};

const SortingContext = createContext<Interaction | undefined>(undefined);

type Sorting =
    | {
          property: string;
          direction: Direction;
          sorting: string | undefined;
      }
    | undefined;

type Action =
    | { type: 'reset' }
    | { type: 'sort'; property: string; direction: Direction }
    | { type: 'toggle'; property: string };

const reducer = (current: Sorting, action: Action): Sorting => {
    switch (action.type) {
        case 'reset':
            return undefined;
        case 'sort': {
            return asSorting(action.property, action.direction);
        }
        case 'toggle': {
            if (action.property === current?.property) {
                return asSorting(current.property, nextDirection(current.direction));
            } else {
                return asSorting(action.property, nextDirection(Direction.None));
            }
        }
        default:
            return current;
    }
};

const asSorting = (property: string, direction: Direction) => {
    return direction === Direction.None
        ? undefined
        : {
              property,
              direction,
              sorting: `${property},${fromDirection(direction)}`
          };
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

const nextDirection = (direction: Direction) => {
    switch (direction) {
        case Direction.None:
            return Direction.Descending;
        case Direction.Descending:
            return Direction.Ascending;
        case Direction.Ascending:
            return Direction.None;
    }
};

type SortingSettings = {
    appendToUrl?: boolean;
};

type SortingProviderProps = {
    children: ReactNode;
} & SortingSettings;

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
        sorting: state?.sorting,
        property: state?.property,
        direction: state?.direction,
        reset: () => dispatch({ type: 'reset' }),
        sortBy: appendToUrl ? sortByParameter : sortByDispatch,
        toggle: (property: string) => dispatch({ type: 'toggle', property })
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

const maybeUseSorting = () => useContext(SortingContext);

export type { Sorting, SortingSettings, Interaction as SortingInteraction };

export { SortingProvider, useSorting, maybeUseSorting };
