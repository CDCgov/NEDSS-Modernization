import { useEffect, useReducer } from 'react';

import { EditableQuestion, PageQuestionControllerService } from 'apps/page-builder/generated';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; page: number; questionId: number }
    | { status: 'complete'; question: EditableQuestion }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; page: number; questionId: number }
    | { type: 'complete'; question: EditableQuestion }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', page: action.page, questionId: action.questionId };
        case 'complete':
            return { status: 'complete', question: action.question };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useFetchEditableQuestion = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'fetching') {
            PageQuestionControllerService.getEditableQuestion({
                page: state.page,
                questionId: state.questionId
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) =>
                    response
                        ? dispatch({ type: 'complete', question: response })
                        : dispatch({ type: 'error', error: 'Failed to retrieve question' })
                );
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        response: state.status === 'complete' ? state.question : undefined,
        fetch: (page: number, questionId: number) => dispatch({ type: 'fetch', page, questionId })
    };

    return value;
};
