import { useEffect, useReducer } from 'react';

import {
    CodedQuestion,
    ConceptControllerService,
    DateQuestion,
    NumericQuestion,
    Page_Concept_ as PageConcept,
    TextQuestion
} from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { Direction } from 'sorting';

export type ConceptSearch = {
    codeSetNm: string;
    page?: number;
    pageSize?: number;
    sort?: ConceptSort;
};

export type Question = TextQuestion | NumericQuestion | CodedQuestion | DateQuestion;

export enum SortField {
    CODE = 'code',
    DISPLAY = 'display',
    CONCEPT_CODE = 'conceptCode',
    EFFECTIVE_DATE = 'effectiveDate'
}
export type ConceptSort = {
    field: SortField;
    direction: Direction;
};

type State =
    | { status: 'idle' }
    | { status: 'searching'; search: ConceptSearch }
    | { status: 'complete'; concepts: PageConcept }
    | { status: 'error'; error: string };

type Action =
    | { type: 'search'; search: ConceptSearch }
    | { type: 'complete'; concepts: PageConcept }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'search':
            return { status: 'searching', search: action.search };
        case 'complete':
            return { status: 'complete', concepts: action.concepts };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useFindConcepts = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'searching') {
            const sortString = state.search.sort
                ? `${state.search.sort.field},${state.search.sort.direction}`
                : undefined;

            ConceptControllerService.searchConceptsUsingPost({
                authorization: authorization(),
                codeSetNm: state.search.codeSetNm,
                page: state.search.page,
                size: state.search.pageSize,
                sort: sortString
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', concepts: response }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'searching',
        response: state.status === 'complete' ? state.concepts : undefined,
        search: (search: ConceptSearch) => dispatch({ type: 'search', search })
    };

    return value;
};
