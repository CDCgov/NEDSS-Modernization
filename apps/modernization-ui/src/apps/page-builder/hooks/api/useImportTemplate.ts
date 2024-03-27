import { useEffect, useReducer } from 'react';
import { TemplateControllerService } from '../../generated';
import { Template } from '../../generated/models/Template';

type State =
    | { status: 'idle' }
    | { status: 'importing'; file: File }
    | { status: 'imported'; template: Template }
    | { status: 'error'; error: string };

type Action =
    | { type: 'reset' }
    | { type: 'import'; file: File }
    | { type: 'imported'; template: Template }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'import':
            return action.file.type === 'text/xml'
                ? { status: 'importing', file: action.file }
                : { status: 'error', error: 'Only XML files are allowed.' };
        case 'imported':
            return { status: 'imported', template: action.template };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useImportTemplate = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'importing') {
            TemplateControllerService.import({
                formData: { file: state.file }
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((template) => {
                    if (template) {
                        dispatch({ type: 'imported', template });
                    } else {
                        dispatch({ type: 'error', error: 'No template was returned' });
                    }
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'importing',
        imported: state.status === 'imported' ? state.template : undefined,
        reset: () => dispatch({ type: 'reset' }),
        importTemplate: (file: File) => dispatch({ type: 'import', file })
    };

    return value;
};
