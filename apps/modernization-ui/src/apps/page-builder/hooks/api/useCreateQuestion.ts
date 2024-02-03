import { authorization } from 'authorization';
import { useEffect, useReducer } from 'react';
import {
    CancelablePromise,
    CodedQuestion,
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    DateQuestion,
    QuestionControllerService,
    TextQuestion
} from '../../generated';

export type CreateQuestionRequest = (
    | CreateNumericQuestionRequest
    | CreateTextQuestionRequest
    | CreateCodedQuestionRequest
    | CreateDateQuestionRequest
) & { questionType: 'CODED' | 'NUMERIC' | 'TEXT' | 'DATE' };

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
            let request: CancelablePromise<CodedQuestion | TextQuestion | DateQuestion | CodedQuestion>;
            switch (state.request.questionType) {
                case 'CODED':
                    request = QuestionControllerService.createCodedQuestionUsingPost({
                        authorization: authorization(),
                        request: state.request as CreateCodedQuestionRequest
                    });
                    break;
                case 'TEXT':
                    request = QuestionControllerService.createTextQuestionUsingPost({
                        authorization: authorization(),
                        request: state.request as CreateTextQuestionRequest
                    });
                    break;
                case 'DATE':
                    request = QuestionControllerService.createDateQuestionUsingPost({
                        authorization: authorization(),
                        request: state.request as CreateDateQuestionRequest
                    });
                    break;
                case 'NUMERIC':
                    request = QuestionControllerService.createNumericQuestionUsingPost({
                        authorization: authorization(),
                        request: state.request as CreateNumericQuestionRequest
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
