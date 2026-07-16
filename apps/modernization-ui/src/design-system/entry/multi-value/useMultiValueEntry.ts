import { useCallback, useReducer } from 'react';

type Entry<V> = { id: string; value: V };

type Lookup<V> = { [id: string]: V };

type Data<V> = {
    values: V[];
    entries: Entry<V>[];
    lookup: Lookup<Entry<V>>;
};

type Selected<V> = { selected: Entry<V> };

type Waiting<V> = { status: 'adding' } & Data<V>;

type State<V> =
    Waiting<V> | ({ status: 'viewing' } & Selected<V> & Data<V>) | ({ status: 'editing' } & Selected<V> & Data<V>);

type Action<V> =
    | { type: 'initialize'; values: V[] }
    | { type: 'add'; item: V }
    | { type: 'edit'; identifier: string }
    | { type: 'update'; item: V }
    | { type: 'delete'; identifier: string }
    | { type: 'view'; identifier: string }
    | { type: 'reset' };

const asData = <V>(next: Lookup<Entry<V>>) =>
    Object.values(next).reduce(
        (existing, entry) => {
            return {
                lookup: existing.lookup,
                values: [...existing.values, entry.value],
                entries: [...existing.entries, entry],
            };
        },
        { values: [], entries: [], lookup: next } as Data<V>
    );

const waiting = <V>(next: Lookup<Entry<V>>): Waiting<V> => {
    const data = asData(next);
    return { status: 'adding', ...data };
};

const initialize =
    <V>(identifierGenerator: () => string) =>
    (values: V[]): State<V> => {
        const entries = values.map((value) => ({ id: identifierGenerator(), value }));
        const lookup = Object.fromEntries(entries.map((entry) => [entry.id, entry]));
        return {
            status: 'adding',
            values: [...values],
            entries,
            lookup,
        };
    };

const reducer =
    <V>(identifierGenerator: () => string) =>
    (current: State<V>, action: Action<V>): State<V> => {
        switch (action.type) {
            case 'initialize': {
                // initialize the state, creating the entries and the lookup map
                return initialize<V>(identifierGenerator)(action.values);
            }
            case 'reset':
                // reset to the default entry state
                return { ...current, status: 'adding' };
            case 'view': {
                // select an item for viewing

                if (current.status == 'viewing' && current.selected.id === action.identifier) {
                    // already viewing the selected item, this is a reset
                    return { ...current, status: 'adding' };
                } else if (action.identifier in current.lookup) {
                    // A new entry was selected to view
                    const selected = current.lookup[action.identifier];
                    return { ...current, status: 'viewing', selected };
                }
                break;
            }
            case 'edit': {
                if (action.identifier in current.lookup) {
                    // select an item for editing
                    const selected = current.lookup[action.identifier];
                    return { ...current, status: 'editing', selected };
                }
                break;
            }
            case 'add': {
                return handleAdd(identifierGenerator, current, action.item);
            }
            case 'update': {
                const next = handleUpdate(current, action.item);
                if (next) {
                    return next;
                } else {
                    break;
                }
            }
            case 'delete': {
                return handleDelete(current, action.identifier);
            }
        }
        return current;
    };

const handleAdd = <V>(identifierGenerator: () => string, current: State<V>, item: V) => {
    const id = identifierGenerator();
    const entry = { id, value: item };
    const lookup = { ...current.lookup, [id]: entry };

    const next = waiting(lookup);
    return next;
};

const handleUpdate = <V>(current: State<V>, item: V) => {
    // must be editing something to update it.
    if (current.status === 'editing') {
        const lookup = { ...current.lookup };

        lookup[current.selected.id] = { id: current.selected.id, value: item };

        const next = waiting(lookup);
        return next;
    }
};

const handleDelete = <V>(current: State<V>, identifier: string) => {
    const lookup = { ...current.lookup };

    delete lookup[identifier];

    if (current.status !== 'adding' && current.selected.id === identifier) {
        // deleted the selected entry, revert to default state
        const next = waiting(lookup);
        return next;
    }

    const { entries, values } = asData(lookup);
    return { ...current, lookup, entries, values };
};

type MultiValueEntryInteraction<V> = {
    /** The values under entry */
    values: V[];
    /** Clears any existing entries, creating new entries using the given values.  
    Any current selection will be reset. */
    using: (values: V[]) => void;
    /** The entries for the current values */
    entries: Entry<V>[];
    /** The state of entry, an entry will be selected if "viewing" or "editing" */
    status: 'adding' | 'viewing' | 'editing';
    /** The currently selected entry */
    selected?: Entry<V>;
    /** Selects the entry identified by the given identifier for viewing */
    view: (identifier: string) => void;
    /** Selects the entry identified by the given identifier for edit */
    edit: (identifier: string) => void;
    /** Resets the selection */
    reset: () => void;
    /** Adds an entry for the given item. */
    add: (item: V) => void;
    /** Updates the currently selected entry being edited with the values provided */
    update: (item: V) => void;
    /** Removes the entry identified by the given identifier */
    remove: (identifier: string) => void;
};

type MultiValueEntrySettings<V> = {
    values: V[];
    identifierGenerator: () => string;
    /** Alerts callers of an added, updated, or removed entry */
    onChange?: (changed: V[]) => void;
};

const useMultiValueEntry = <E>({
    values,
    identifierGenerator,
    onChange,
}: MultiValueEntrySettings<E>): MultiValueEntryInteraction<E> => {
    const [state, dispatch] = useReducer(reducer<E>(identifierGenerator), values, initialize(identifierGenerator));

    const selected = state.status === 'editing' || state.status === 'viewing' ? state.selected : undefined;

    const using = useCallback((values: E[]) => dispatch({ type: 'initialize', values }), [dispatch]);

    // selection actions
    const view = useCallback((identifier: string) => dispatch({ type: 'view', identifier }), [dispatch]);
    const edit = useCallback((identifier: string) => dispatch({ type: 'edit', identifier }), [dispatch]);
    const reset = useCallback(() => dispatch({ type: 'reset' }), [dispatch]);

    // mutation actions - defer to onChange to update the data, which will then reset the state here
    const add = (item: E) => {
        if (onChange) {
            const { values } = handleAdd(identifierGenerator, state, item);
            onChange(values);
        } else {
            dispatch({ type: 'add', item });
        }
    };
    const update = (item: E) => {
        if (onChange) {
            const next = handleUpdate(state, item);
            if (next) onChange(next.values);
        } else {
            dispatch({ type: 'update', item });
        }
    };
    const remove = (identifier: string) => {
        if (onChange) {
            const { values } = handleDelete(state, identifier);
            onChange(values);
        } else {
            dispatch({ type: 'delete', identifier });
        }
    };

    return {
        status: state.status,
        entries: state.entries,
        values: state.values,
        selected,
        using,
        add,
        edit,
        update,
        remove,
        view,
        reset,
    };
};

export { useMultiValueEntry };
export type { MultiValueEntrySettings, MultiValueEntryInteraction, Entry };
