/* eslint-disable camelcase */
import { useContext, useEffect, useReducer } from 'react';
import { UserContext } from 'user';
import {
    AddableQuestionControllerService,
    CodedQuestion,
    DateQuestion,
    NumericQuestion,
    Page_AddableQuestion_,
    TextQuestion
} from '../../generated';
import { Direction } from 'sorting';

export type QuestionSearch = {
    searchText?: string;
    pageId: number;
    page?: number;
    pageSize?: number;
    sort?: AddableQuestionSort;
};

export type Question = TextQuestion | NumericQuestion | CodedQuestion | DateQuestion;

export enum SortField {
    TYPE = 'type',
    UNIQUE_ID = 'uniqueId',
    UNIQUE_NAME = 'uniqueName',
    SUBGROUP = 'subgroup'
}
export type AddableQuestionSort = {
    field: SortField;
    direction: Direction;
};

type State =
    | { status: 'idle' }
    | { status: 'searching'; search: QuestionSearch }
    | { status: 'complete'; questions: Page_AddableQuestion_ }
    | { status: 'error'; error: string };

type Action =
    | { type: 'search'; search: QuestionSearch }
    | { type: 'complete'; questions: Page_AddableQuestion_ }
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
    const {
        state: { getToken }
    } = useContext(UserContext);
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'searching') {
            const sortString = state.search.sort
                ? `${state.search.sort.field},${state.search.sort.direction}`
                : undefined;

            AddableQuestionControllerService.findAddableQuestionsUsingPost({
                authorization: `Bearer ${getToken()}`,
                request: { query: state.search.searchText ?? '' },
                pageId: state.search.pageId,
                page: state.search.page,
                size: state.search.pageSize,
                sort: sortString
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', questions: response }));
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
