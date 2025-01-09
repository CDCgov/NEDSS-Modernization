import { useReducer } from 'react';

type State<V> =
    | { status: 'adding'; data: V[] }
    | { status: 'viewing'; data: V[]; index: number; selected: V }
    | { status: 'editing'; data: V[]; index: number; selected: V };

type Action<V> =
    | { type: 'add'; item: V }
    | { type: 'edit'; index: number }
    | { type: 'update'; index: number; item: V }
    | { type: 'delete'; index: number }
    | { type: 'view'; index: number }
    | { type: 'reset' };

const reducer = <V>(current: State<V>, action: Action<V>): State<V> => {
    switch (action.type) {
        case 'add':
            return { status: 'adding', data: [...current.data, action.item] };
        case 'view':
            return { ...current, status: 'viewing', index: action.index, selected: current.data[action.index] };
        case 'edit':
            return { ...current, status: 'editing', index: action.index, selected: current.data[action.index] };
        case 'update': {
            const data = [...current.data];

            data[action.index] = action.item;
            return { status: 'adding', data };
        }
        case 'delete': {
            const data = [...current.data];
            data.splice(action.index, 1);
            if (current.status === 'adding') {
                return { ...current, data };
            } else {
                if (current.index === action.index) {
                    // currently editing or viewing the deleted entry
                    return { status: 'adding', data };
                } else if (current.index > action.index) {
                    // editing or viewing an entry with index > than deleted index. updated index - 1
                    return {
                        status: current.status,
                        data,
                        index: current.index - 1,
                        selected: data[current.index - 1]
                    };
                } else {
                    // editing or viewing an entry with index < than deleted index. keep current index
                    return { ...current, data };
                }
            }
        }
        case 'reset':
            return { status: 'adding', data: current.data };
    }
};

type Interaction<V> = {
    state: State<V>;
    status: 'adding' | 'viewing' | 'editing';
    selected?: V;
    add: (item: V) => void;
    edit: (index: number) => void;
    update: (index: number, item: V) => void;
    remove: (index: number) => void;
    view: (index: number) => void;
    reset: () => void;
};

type Settings<V> = {
    values?: V[];
};

const useMultiValueEntryState = <V>({ values = [] }: Settings<V>): Interaction<V> => {
    const [state, dispatch] = useReducer(reducer<V>, { status: 'adding', data: values });

    const selected = state.status === 'editing' || state.status === 'viewing' ? state.selected : undefined;

    return {
        state,
        status: state.status,
        selected,
        add: (item: V) => dispatch({ type: 'add', item }),
        edit: (index: number) => dispatch({ type: 'edit', index }),
        update: (index: number, item: V) => dispatch({ type: 'update', index, item }),
        remove: (index: number) => dispatch({ type: 'delete', index }),
        view: (index: number) => dispatch({ type: 'view', index }),
        reset: () => dispatch({ type: 'reset' })
    };
};

export { useMultiValueEntryState };
