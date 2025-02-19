import { useReducer } from 'react';

type State<V> =
    | { status: 'adding'; data: V[] }
    | { status: 'viewing'; data: V[]; selected: V }
    | { status: 'editing'; data: V[]; selected: V };

type Action<V> =
    | { type: 'add'; item: V }
    | { type: 'edit'; item: V }
    | { type: 'update'; item: V }
    | { type: 'delete'; item: V }
    | { type: 'view'; item: V }
    | { type: 'reset' };

const reducer = <V>(current: State<V>, action: Action<V>): State<V> => {
    switch (action.type) {
        case 'reset':
            return { status: 'adding', data: current.data };
        case 'view': {
            const i = current.data.indexOf(action.item);
            if (i >= 0) {
                return { ...current, status: 'viewing', selected: action.item };
            }
            break;
        }
        case 'edit': {
            const i = current.data.indexOf(action.item);
            if (i >= 0) {
                return { ...current, status: 'editing', selected: action.item };
            }
            break;
        }
        case 'add': {
            const data = [...current.data, action.item];
            return { status: 'adding', data };
        }
        case 'update': {
            if (current.status === 'editing') {
                const i = current.data.indexOf(current.selected);
                const data = [...current.data];

                data[i] = action.item;
                return { status: 'adding', data };
            }
            break;
        }
        case 'delete': {
            const i = current.data.indexOf(action.item);
            const data = [...current.data];
            data.splice(i, 1);

            if (current.status !== 'adding') {
                if (current.selected === action.item) {
                    // currently editing or viewing the deleted entry
                    return { status: 'adding', data };
                } else {
                    // editing or viewing an entry with index > than deleted index. updated index - 1
                    return {
                        status: current.status,
                        data,
                        selected: current.selected
                    };
                }
            }

            return { ...current, data };
        }
    }
    return current;
};

type Interaction<V> = {
    status: 'adding' | 'viewing' | 'editing';
    entries: V[];
    selected?: V;
    add: (item: V) => void;
    edit: (item: V) => void;
    update: (item: V) => void;
    remove: (item: V) => void;
    view: (item: V) => void;
    reset: () => void;
};

type Settings<V> = {
    values?: V[];
};

const useMultiValueEntryState = <V>({ values = [] }: Settings<V>): Interaction<V> => {
    const [state, dispatch] = useReducer(reducer<V>, { status: 'adding', data: values });

    const selected = state.status === 'editing' || state.status === 'viewing' ? state.selected : undefined;

    return {
        status: state.status,
        entries: state.data,
        selected,
        add: (item: V) => dispatch({ type: 'add', item }),
        edit: (item: V) => dispatch({ type: 'edit', item }),
        update: (item: V) => dispatch({ type: 'update', item }),
        remove: (item: V) => dispatch({ type: 'delete', item }),
        view: (item: V) => dispatch({ type: 'view', item }),
        reset: () => dispatch({ type: 'reset' })
    };
};

export { useMultiValueEntryState };
