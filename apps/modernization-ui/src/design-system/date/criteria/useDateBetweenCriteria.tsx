import { useReducer } from 'react';
import { DateBetweenCriteria } from '../entry';

export type DateRangeEntryFields = 'from' | 'to';

type Action =
    | { type: 'apply'; field: DateRangeEntryFields; value: string }
    | { type: 'clear'; field: DateRangeEntryFields };

const dateRangeReducer = (state: DateBetweenCriteria, action: Action): DateBetweenCriteria => {
    switch (action.type) {
        case 'apply':
            return {
                between: { ...state.between, [action.field]: action.value }
            };
        case 'clear': {
            const newState = { between: { ...state.between } };
            delete newState.between[action.field as DateRangeEntryFields];
            return newState;
        }
        default:
            return state;
    }
};

export const useDateBetweenCriteria = (initialValue: DateBetweenCriteria) => {
    const [state, dispatch] = useReducer(dateRangeReducer, initialValue);

    const apply = (field: DateRangeEntryFields, value: string) => {
        dispatch({ type: 'apply', field, value });
    };

    const clear = (field: DateRangeEntryFields) => {
        dispatch({ type: 'clear', field });
    };

    return { state, apply, clear };
};
