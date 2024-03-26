import { useEffect, useReducer } from 'react';

import { EditableQuestion, PageQuestionControllerService } from 'apps/page-builder/generated';

type State =
    | { status: 'idle' }
    | {
          status: 'updating';
          page: number;
          question: number;
          required: boolean;
      }
    | { status: 'complete'; response: EditableQuestion }
    | { status: 'error'; error: string };

type Action =
    | {
          type: 'update';
          page: number;
          question: number;
          required: boolean;
      }
    | { type: 'complete'; response: EditableQuestion }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'update':
            return { status: 'updating', page: action.page, question: action.question, required: action.required };
        case 'complete':
            return { status: 'complete', response: action.response };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useSetPageQuestionRequired = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'updating') {
            PageQuestionControllerService.updatePageQuestionRequired({
                questionId: state.question,
                page: state.page,
                requestBody: { required: state.required }
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    if (response) {
                        dispatch({ type: 'complete', response: response });
                    }
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'updating',
        response: state.status === 'complete' ? state.response : undefined,
        setRequired: (page: number, question: number, required: boolean) =>
            dispatch({ type: 'update', page, question, required })
    };

    return value;
};
