import { useEffect, useReducer } from 'react';

import {
    AvailableQuestionControllerService,
    CodedQuestion,
    DateQuestion,
    NumericQuestion,
    PageAvailableQuestion,
    TextQuestion
} from 'apps/page-builder/generated';
import { Direction } from 'libs/sorting';

export type QuestionSearch = {
    searchText?: string;
    pageId: number;
    page?: number;
    pageSize?: number;
    sort?: AddableQuestionSort;
};

export type Question = TextQuestion | NumericQuestion | CodedQuestion | DateQuestion;

export enum SortField {
    STATUS = 'status',
    TYPE = 'type',
    UNIQUE_ID = 'uniqueId',
    LABEL = 'label',
    SUBGROUP = 'subgroup'
}
export type AddableQuestionSort = {
    field: SortField;
    direction: Direction;
};

type State =
    | { status: 'idle' }
    | { status: 'searching'; search: QuestionSearch }
    | { status: 'complete'; questions: PageAvailableQuestion }
    | { status: 'error'; error: string };

type Action =
    | { type: 'search'; search: QuestionSearch }
    | { type: 'complete'; questions: PageAvailableQuestion }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'search':
            return { status: 'searching', search: action.search };
        case 'complete':
            return { status: 'complete', questions: action.questions };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useFindAddableQuestions = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'searching') {
            const sortString = state.search.sort
                ? `${state.search.sort.field},${state.search.sort.direction}`
                : undefined;

            AvailableQuestionControllerService.findAvailableQuestions({
                pageId: state.search.pageId,
                requestBody: {
                    query: state.search.searchText ?? ''
                },
                page: state.search.page,
                size: state.search.pageSize,
                sort: sortString ? [sortString] : undefined
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    if (response) {
                        dispatch({ type: 'complete', questions: response });
                    } else {
                        dispatch({ type: 'error', error: 'Failed to find available questions' });
                    }
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'searching',
        response: state.status === 'complete' ? state.questions : undefined,
        search: (search: QuestionSearch) => dispatch({ type: 'search', search })
    };

    return value;
};
