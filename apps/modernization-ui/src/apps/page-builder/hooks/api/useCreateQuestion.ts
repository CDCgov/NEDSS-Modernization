import { useEffect, useReducer } from 'react';
import {
    CancelablePromise,
    CodedQuestion,
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    DateQuestion,
    NumericQuestion,
    QuestionControllerService,
    TextQuestion
} from '../../generated';

export type CreateQuestionRequest =
    | (CreateNumericQuestionRequest & { questionType: 'NUMERIC' })
    | (CreateTextQuestionRequest & { questionType: 'TEXT' })
    | (CreateCodedQuestionRequest & { questionType: 'CODED' })
    | (CreateDateQuestionRequest & { questionType: 'DATE' });

type State =
    | { status: 'idle' }
    | { status: 'creating'; request: CreateQuestionRequest }
    | { status: 'complete'; id: number }
    | { status: 'error'; error: string };

type Action =
    | { type: 'create'; request: CreateQuestionRequest }
    | { type: 'complete'; id: number }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'create':
            return { status: 'creating', request: action.request };
        case 'complete':
            return { status: 'complete', id: action.id };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useCreateQuestion = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'creating') {
            let request: CancelablePromise<CodedQuestion | TextQuestion | DateQuestion | NumericQuestion>;
            switch (state.request.questionType) {
                case 'CODED':
                    request = QuestionControllerService.createCodedQuestion({
                        requestBody: state.request as CreateCodedQuestionRequest
                    });
                    break;
                case 'TEXT':
                    request = QuestionControllerService.createTextQuestion({
                        requestBody: state.request as CreateTextQuestionRequest
                    });
                    break;
                case 'DATE':
                    request = QuestionControllerService.createDateQuestion({
                        requestBody: state.request as CreateDateQuestionRequest
                    });
                    break;
                case 'NUMERIC':
                    request = QuestionControllerService.createNumericQuestion({
                        requestBody: state.request as CreateNumericQuestionRequest
                    });
                    break;
                default:
                    dispatch({ type: 'error', error: 'Failed to create question' });
                    throw new Error('Failed to determine question type.');
            }

            request
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => response?.id && dispatch({ type: 'complete', id: response.id }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'creating',
        questionId: state.status === 'complete' ? state.id : undefined,
        createQuestion: (request: CreateQuestionRequest) => dispatch({ type: 'create', request })
    };

    return value;
};
