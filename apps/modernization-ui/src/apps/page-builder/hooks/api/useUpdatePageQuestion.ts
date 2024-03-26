import {
    CancelablePromise,
    EditableQuestion,
    PageQuestionControllerService,
    UpdatePageCodedQuestionRequest,
    UpdatePageDateQuestionRequest,
    UpdatePageNumericQuestionRequest,
    UpdatePageTextQuestionRequest
} from 'apps/page-builder/generated';
import { useEffect, useReducer } from 'react';

export type UpdatePageQuestionRequest =
    | (UpdatePageTextQuestionRequest & { questionType: 'TEXT' })
    | (UpdatePageDateQuestionRequest & { questionType: 'DATE' })
    | (UpdatePageNumericQuestionRequest & { questionType: 'NUMERIC' })
    | (UpdatePageCodedQuestionRequest & { questionType: 'CODED' });

type State =
    | { status: 'idle' }
    | { status: 'updating'; page: number; questionId: number; request: UpdatePageQuestionRequest }
    | { status: 'complete'; response: EditableQuestion }
    | { status: 'error'; error: string };

type Action =
    | { type: 'update'; page: number; questionId: number; request: UpdatePageQuestionRequest }
    | { type: 'complete'; response: EditableQuestion }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'update':
            return { status: 'updating', page: action.page, questionId: action.questionId, request: action.request };
        case 'complete':
            return { status: 'complete', response: action.response };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useUpdatePageQuestion = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'updating') {
            let request: CancelablePromise<EditableQuestion>;

            switch (state.request.questionType) {
                case 'CODED':
                    request = PageQuestionControllerService.updatePageCodedQuestion({
                        page: state.page,
                        questionId: state.questionId,
                        requestBody: state.request
                    });
                    break;
                case 'TEXT':
                    request = PageQuestionControllerService.updatePageTextQuestion({
                        page: state.page,
                        questionId: state.questionId,
                        requestBody: state.request
                    });
                    break;
                case 'DATE':
                    request = PageQuestionControllerService.updatePageDateQuestion({
                        page: state.page,
                        questionId: state.questionId,
                        requestBody: state.request
                    });
                    break;
                case 'NUMERIC':
                    request = PageQuestionControllerService.updatePageNumericQuestion({
                        page: state.page,
                        questionId: state.questionId,
                        requestBody: state.request
                    });
                    break;
                default:
                    dispatch({ type: 'error', error: 'Failed to update question' });
                    throw new Error('Failed to determine question type.');
            }
            request
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => response && dispatch({ type: 'complete', response: response }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'updating',
        response: state.status === 'complete' ? state.response : undefined,
        update: (page: number, questionId: number, request: UpdatePageQuestionRequest) =>
            dispatch({ type: 'update', page, questionId, request })
    };

    return value;
};
