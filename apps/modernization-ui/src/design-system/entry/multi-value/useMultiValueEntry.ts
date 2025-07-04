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
    | Waiting<V>
    | ({ status: 'viewing' } & Selected<V> & Data<V>)
    | ({ status: 'editing' } & Selected<V> & Data<V>);

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
                entries: [...existing.entries, entry]
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
    (values: V[]): State<V> =>
        values.reduce(
            (existing, value) => {
                const id = identifierGenerator();
                const entry = { id, value };
                return {
                    status: existing.status,
                    values,
                    entries: [...existing.entries, entry],
                    lookup: { ...existing.lookup, [id]: entry }
                };
            },
            { status: 'adding', values: [...values], entries: [], lookup: {} } as Waiting<V>
        );

const reducer =
    <V>(identifierGenerator: () => string, onChange?: (changed: V[]) => void) =>
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
                const id = identifierGenerator();
                const entry = { id, value: action.item };
                const lookup = { ...current.lookup, [id]: entry };

                const next = waiting(lookup);
                //
                onChange?.(next.values);
                return next;
            }
            case 'update': {
                if (current.status === 'editing') {
                    // must be editing something to update it.
                    const lookup = { ...current.lookup };

                    lookup[current.selected.id] = { id: current.selected.id, value: action.item };

                    const next = waiting(lookup);
                    onChange?.(next.values);
                    return next;
                }
                break;
            }
            case 'delete': {
                const identifier = action.identifier;
                const lookup = { ...current.lookup };

                delete lookup[identifier];

                if (current.status !== 'adding' && current.selected.id === identifier) {
                    // deleted the selected entry, revert to default state
                    const next = waiting(lookup);
                    onChange?.(next.values);
                    return next;
                }

                const { entries, values } = asData(lookup);
                onChange?.(values);
                return { ...current, lookup, entries, values };
            }
        }
        return current;
    };

type MultiValueEntryInteraction<V> = {
    /** The values under entry */
    values: V[];
    /** Clears any existing entries, creating new entries using the given values.  Any current selection will be reset. */
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
    onChange
}: MultiValueEntrySettings<E>): MultiValueEntryInteraction<E> => {
    const [state, dispatch] = useReducer(
        reducer<E>(identifierGenerator, onChange),
        values,
        initialize(identifierGenerator)
    );

    const selected = state.status === 'editing' || state.status === 'viewing' ? state.selected : undefined;

    const using = useCallback((values: E[]) => dispatch({ type: 'initialize', values }), [dispatch]);

    // selection actions
    const view = useCallback((identifier: string) => dispatch({ type: 'view', identifier }), [dispatch]);
    const edit = useCallback((identifier: string) => dispatch({ type: 'edit', identifier }), [dispatch]);
    const reset = useCallback(() => dispatch({ type: 'reset' }), [dispatch]);

    // mutation actions
    const add = useCallback((item: E) => dispatch({ type: 'add', item }), [dispatch]);
    const update = useCallback((item: E) => dispatch({ type: 'update', item }), [dispatch]);
    const remove = useCallback((identifier: string) => dispatch({ type: 'delete', identifier }), [dispatch]);

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
        reset
    };
};

export { useMultiValueEntry };
export type { MultiValueEntrySettings, MultiValueEntryInteraction, Entry };
