import { Selectable, isEqual } from 'options';
import { useReducer } from 'react';
import { mapIf } from 'utils';

type Item = { selected: boolean; value: Selectable };
type Selections = {} | { [key: string]: Item };
type State = {
    items: Item[];
    selected: Selectable[];
    selections: Selections;
};

const asItems = (selections: Selections) => Object.values(selections);
const asSelected = (selections: Selections) =>
    mapIf(
        (item) => item.value,
        (item) => item.selected,
        Object.values(selections)
    );

const updateState = (selections: Selections): State => ({
    selections,
    items: asItems(selections),
    selected: asSelected(selections)
});

const asSelections =
    (selected: boolean) =>
    (selectable: Selectable): Selections => ({
        [selectable.value]: { selected, value: selectable }
    });

const withItemSelected = (current: State, selectable: Selectable) => {
    const selections = { ...current.selections, ...asSelections(true)(selectable) };
    return updateState(selections);
};
const withItemDeselected = (current: State, selectable: Selectable) => {
    const selections = { ...current.selections, ...asSelections(false)(selectable) };
    return updateState(selections);
};

const contains = (selected: Selectable[]) => (selectable: Selectable) =>
    selected.find(isEqual(selectable)) != undefined;

const resolveItemSelection = (selected: Selectable[]) => (selectable: Selectable) => {
    const isSelected = contains(selected)(selectable);

    return { ...asSelections(isSelected)(selectable) };
};

const initialize =
    (selected: Selectable[]) =>
    (available: Selectable[]): State => {
        const resolve = resolveItemSelection(selected);

        const selections = available.reduce((existing: Selections, next: Selectable) => {
            return { ...existing, ...resolve(next) };
        }, {});

        return updateState(selections);
    };

const reset = (selected: Selectable[], state: State): State => {
    const resolve = resolveItemSelection(selected);
    const selections = Object.entries(state.selections).reduce(
        (existing: Selections, [, item]: [string, Item]) => ({ ...existing, ...resolve(item.value) }),
        {}
    );
    return updateState(selections);
};

type Reset = { type: 'reset'; selected: Selectable[] };
type Select = { type: 'select'; item: Selectable };
type Deselect = { type: 'deselect'; item: Selectable };

type Action = Reset | Select | Deselect;

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'select': {
            return withItemSelected(current, action.item);
        }
        case 'deselect': {
            return withItemDeselected(current, action.item);
        }
        case 'reset': {
            return reset(action.selected, current);
        }
    }
};

type Interaction = {
    items: Item[];
    selected: Selectable[];
    reset: (selected?: Selectable[]) => void;
    select: (item: Selectable) => void;
    deselect: (item: Selectable) => void;
};

type Settings = {
    available: Selectable[];
    selected?: Selectable[];
};

const useMultiSelection = ({ available, selected = [] }: Settings): Interaction => {
    const [state, dispatch] = useReducer(reducer, available, initialize(selected));

    const reset = (selected: Selectable[] = []) => dispatch({ type: 'reset', selected });
    const select = (item: Selectable) => dispatch({ type: 'select', item });
    const deselect = (item: Selectable) => dispatch({ type: 'deselect', item });

    return {
        items: state.items,
        selected: state.selected,
        reset,
        select,
        deselect
    };
};

export { useMultiSelection };
