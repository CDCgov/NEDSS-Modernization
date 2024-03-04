import { PageRuleControllerService, Pageable, Rule, SearchPageRuleRequest, Sort } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useReducer } from 'react';
import { useParams } from 'react-router-dom';
import { Direction } from 'sorting/Sort';

export type PageRules = {
    content?: Rule[];
    empty?: boolean;
    first?: boolean;
    last?: boolean;
    number?: number;
    numberOfElements?: number;
    pageable?: Pageable;
    size?: number;
    sort?: Sort;
    totalElements?: number;
    totalPages?: number;
};

export type FetchBusinessRules = {
    pageId?: number;
    page?: number;
    pageSize?: number;
    sort?: BusinessRuleSort;
    query?: string;
};

export enum RuleSortField {
    SOURCE = 'sourcefields',
    FUNCTION = 'function',
    VALUE = 'values',
    TARGET = 'target',
    ID = 'id',
    LOGIC = 'logic'
}

export type BusinessRuleSort = {
    field: RuleSortField;
    direction: Direction;
};

type State =
    | { status: 'idle' }
    | { status: 'searching'; search: FetchBusinessRules }
    | { status: 'complete'; rules: PageRules | void }
    | { status: 'error'; error: string };

type Action =
    | { type: 'search'; search: FetchBusinessRules }
    | { type: 'complete'; rules: PageRules | void }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'search':
            return { status: 'searching', search: action.search };
        case 'complete':
            return { status: 'complete', rules: action.rules };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useFetchPageRules = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    const { pageId } = useParams();

    useEffect(() => {
        if (state.status === 'searching' && pageId) {
            const sortString = state.search.sort
                ? `${state.search.sort.field},${state.search.sort.direction}`
                : undefined;

            const request: SearchPageRuleRequest = { searchValue: state.search.query };

            PageRuleControllerService.findPageRuleUsingPost({
                authorization: authorization(),
                id: Number(pageId),
                page: state.search.page,
                size: state.search.pageSize,
                sort: sortString,
                request: request
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', rules: response }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'searching',
        response: state.status === 'complete' ? state.rules : undefined,
        search: (search: FetchBusinessRules) => dispatch({ type: 'search', search })
    };

    return value;
};
