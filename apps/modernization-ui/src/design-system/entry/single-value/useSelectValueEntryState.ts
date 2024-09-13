import { useReducer } from 'react';
import { FieldValues } from 'react-hook-form';

type State<V> = {
    status: 'editing';
    data: V | null;
};

type Action<V> = { type: 'update'; item: V } | { type: 'reset' };

const useSelectValueEntryState = <V extends FieldValues>() => {
    const reducer = (_state: State<V>, action: Action<V>): State<V> => {
        switch (action.type) {
            case 'update':
                return { status: 'editing', data: action.item };
            case 'reset':
                return { status: 'editing', data: null };
        }
    };
    const [state, dispatch] = useReducer(reducer, { status: 'editing', data: null });

    return {
        state,
        update: (item: V) => dispatch({ type: 'update', item }),
        reset: () => dispatch({ type: 'reset' })
    };
};

export { useSelectValueEntryState };
