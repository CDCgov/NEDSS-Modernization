import { useReducer } from 'react';
import { DateEqualsCriteria } from '../entry';

export type ExactDateEntryFields = 'day' | 'month' | 'year';

type Action =
    | { type: 'apply'; field: ExactDateEntryFields; value: number }
    | { type: 'clear'; field: ExactDateEntryFields };

const dateReducer = (state: DateEqualsCriteria, action: Action): DateEqualsCriteria => {
    switch (action.type) {
        case 'apply':
            return {
                equals: { ...state.equals, [action.field]: action.value }
            };
        case 'clear': {
            const newState = { equals: { ...state.equals } };
            delete newState.equals[action.field as ExactDateEntryFields];
            return newState;
        }
        default:
            return state;
    }
};

export const useDateEqualsCriteria = (initialValue: DateEqualsCriteria) => {
    const [state, dispatch] = useReducer(dateReducer, initialValue);

    const apply = (field: ExactDateEntryFields, value: number) => {
        dispatch({ type: 'apply', field, value });
    };

    const clear = (field: ExactDateEntryFields) => {
        dispatch({ type: 'clear', field });
    };

    return { state, apply, clear };
};
