import { Condition, ConditionControllerService } from 'apps/page-builder/generated';
import { useEffect, useReducer } from 'react';
import { Direction } from 'libs/sorting';

export type ConditionSearch = {
    searchText?: string;
    page?: number;
    pageSize?: number;
    sort?: ConditionSort;
};

export enum ConditionSortField {
    CONDITION = 'conditionShortNm',
    CODE = 'id',
    PROGRAM_AREA = 'progAreaCd',
    CONDITION_FAMILY = 'familyCd',
    COINFECTION_GROUP = 'coinfection_grp_cd',
    NND = 'nndInd',
    INVESTIGATION_PAGE = 'investigationFormCd',
    STATUS = 'statusCd'
}
export type ConditionSort = {
    field: ConditionSortField;
    direction: Direction;
};

type ResultPage = {
    content?: Array<Condition>;
    empty?: boolean;
    first?: boolean;
    last?: boolean;
    number?: number;
    numberOfElements?: number;
    size?: number;
    totalElements?: number;
    totalPages?: number;
};

type State =
    | { status: 'idle' }
    | { status: 'searching'; search: ConditionSearch }
    | { status: 'complete'; response: ResultPage; search: ConditionSearch }
    | { status: 'error'; error: string };

type Action =
    | { type: 'search'; search: ConditionSearch }
    | { type: 'complete'; response: ResultPage; search: ConditionSearch }
    | { type: 'error'; error: string }
    | { type: 'reset' };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'search':
            return { status: 'searching', search: action.search };
        case 'complete':
            return { status: 'complete', response: action.response, search: action.search };
        case 'error':
            return { status: 'error', error: action.error };
        case 'reset':
        default:
            return { ...initial };
    }
};

export const useConditionSearch = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'searching') {
            const sortString = state.search.sort
                ? `${state.search.sort.field},${state.search.sort.direction}`
                : `${ConditionSortField.CONDITION},${Direction.Ascending}`;

            ConditionControllerService.searchConditions({
                requestBody: {
                    searchText: state.search.searchText ?? '',
                    excludeInUse: true
                },
                page: state.search.page,
                size: state.search.pageSize,
                sort: sortString ? [sortString] : undefined
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    if (response) {
                        dispatch({ type: 'complete', response, search: state.search });
                    } else {
                        dispatch({ type: 'error', error: 'Failed to find conditions' });
                    }
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'searching',
        response: state.status === 'complete' ? state.response : undefined,
        search: (search: ConditionSearch) => dispatch({ type: 'search', search }),
        keyword: state.status === 'complete' || state.status === 'searching' ? state.search.searchText : '',
        reset: () => dispatch({ type: 'reset' })
    };

    return value;
};
