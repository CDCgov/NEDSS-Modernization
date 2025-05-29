import { Direction } from 'libs/sorting';
import { useState } from 'react';

type SortHandler = (name: string, type: Direction) => void;

type SortResolver = (name: string) => Direction;

type SortState = {
    [key: string]: Direction;
};

export type Sorting = {
    enabled: boolean;
    currentDirection: SortResolver;
    isSorting: (name: string) => boolean;
    toggleSort: (name: string) => void;
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

const initialState: SortState = {};

type Props = {
    enabled: boolean;
    onSort?: SortHandler;
};

const useTableSorting = ({ enabled, onSort }: Props): Sorting => {
    const [sortState, setSortState] = useState<SortState>(initialState);

    const resolveDirection: SortResolver = (name: string) => sortState[name] ?? Direction.None;

    const handleSort = (resolve: SortResolver) => (name: string) => {
        const next = nextDirection(resolve(name));

        onSort?.(name, next);

        setSortState({
            ...initialState,
            [name]: next
        });
    };

    const isSorting = (resolve: SortResolver) => (header: string) => resolve(header) !== Direction.None;

    return {
        enabled,
        currentDirection: resolveDirection,
        isSorting: isSorting(resolveDirection),
        toggleSort: handleSort(resolveDirection)
    };
};

export { useTableSorting };
export type { SortHandler };
