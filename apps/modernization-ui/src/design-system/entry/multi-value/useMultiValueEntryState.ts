import { useReducer } from 'react';

type State<V> =
    | { status: 'adding'; data: V[] }
    | { status: 'viewing'; data: V[]; index: number }
    | { status: 'editing'; data: V[]; index: number };

type Action<V> =
    | { type: 'add'; item: V }
    | { type: 'edit'; index: number }
    | { type: 'update'; index: number; item: V }
    | { type: 'delete'; index: number }
    | { type: 'view'; index: number }
    | { type: 'reset' };

const useMultiValueEntryState = <V>() => {
    const reducer = (_state: State<V>, action: Action<V>): State<V> => {
        const data = [..._state.data];
        switch (action.type) {
            case 'add':
                return { status: 'adding', data: [...data, action.item] };
            case 'view':
                return { status: 'viewing', data, index: action.index };
            case 'edit':
                return { status: 'editing', data, index: action.index };
            case 'update':
                data[action.index] = action.item;
                return { status: 'adding', data };
            case 'delete':
                data.splice(action.index, 1);
                if (_state.status === 'viewing' && _state.index === action.index) {
                    return { status: 'adding', data };
                } else {
                    return { ..._state, data };
                }
            case 'reset':
                return { status: 'adding', data: _state.data };
        }
    };
    const [state, dispatch] = useReducer(reducer, { status: 'adding', data: [] });

    return {
        state,
        add: (item: V) => dispatch({ type: 'add', item }),
        edit: (index: number) => dispatch({ type: 'edit', index }),
        update: (index: number, item: V) => dispatch({ type: 'update', index, item }),
        remove: (index: number) => dispatch({ type: 'delete', index }),
        view: (index: number) => dispatch({ type: 'view', index }),
        reset: () => dispatch({ type: 'reset' })
    };
};

export { useMultiValueEntryState };
