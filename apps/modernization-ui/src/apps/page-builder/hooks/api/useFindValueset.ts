import { useEffect, useReducer } from 'react';

import { ValueSetControllerService, PageValueSetOption } from 'apps/page-builder/generated';
import { Direction } from 'libs/sorting';

export type ValuesetSearch = {
    query?: string;
    page?: number;
    pageSize?: number;
    sort?: ValuesetSort;
};

export enum SortField {
    TYPE = 'type',
    NAME = 'name',
    DESCRIPTION = 'description',
    CODE = 'code'
}
export type ValuesetSort = {
    field: SortField;
    direction: Direction;
};

type State =
    | { status: 'idle' }
    | { status: 'searching'; search: ValuesetSearch }
    | { status: 'complete'; valuesets: PageValueSetOption }
    | { status: 'error'; error: string };

type Action =
    | { type: 'search'; search: ValuesetSearch }
    | { type: 'complete'; valuesets: PageValueSetOption }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'search':
            return { status: 'searching', search: action.search };
        case 'complete':
            return { status: 'complete', valuesets: action.valuesets };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useFindValuesets = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'searching') {
            const sortString = state.search.sort
                ? `${state.search.sort.field},${state.search.sort.direction}`
                : undefined;

            ValueSetControllerService.searchValueSet({
                requestBody: { query: state.search.query ?? '' },
                page: state.search.page,
                size: state.search.pageSize,
                sort: sortString ? [sortString] : undefined
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    if (response) {
                        dispatch({ type: 'complete', valuesets: response });
                    } else {
                        dispatch({ type: 'error', error: 'Failed to find Valuesets' });
                    }
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'searching',
        response: state.status === 'complete' ? state.valuesets : undefined,
        search: (search: ValuesetSearch) => dispatch({ type: 'search', search })
    };

    return value;
};
