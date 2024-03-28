import { QuestionValidationRequest } from 'apps/page-builder/generated/models/QuestionValidationRequest';
import { QuestionControllerHelperService } from 'apps/page-builder/generated/services/QuestionControllerHelperService';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'validating'; field: QuestionValidationRequest.field; value: string }
    | { status: 'complete'; isValid: boolean }
    | { status: 'error'; error: string };

type Action =
    | { type: 'validate'; field: QuestionValidationRequest.field; value: string }
    | { type: 'complete'; isValid: boolean }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'validate':
            return { status: 'validating', field: action.field, value: action.value };
        case 'complete':
            return { status: 'complete', isValid: action.isValid };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const useQuestionValidation = (field: QuestionValidationRequest.field) => {
    const [state, dispatch] = useReducer(reducer, { status: 'idle' });

    useEffect(() => {
        if (state.status === 'validating') {
            QuestionControllerHelperService.validate({
                requestBody: { value: state.value, field: state.field }
            })
                .then((response) => dispatch({ type: 'complete', isValid: response.isValid ?? false }))
                .catch(() => console.error(`Failed to validate ${state.field}`));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'validating',
        isValid: state.status === 'complete' ? state.isValid : undefined,
        validate: (value: string) => dispatch({ type: 'validate', field, value })
    };
    return value;
};
