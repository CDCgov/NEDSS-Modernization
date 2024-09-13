import { Mapping, Maybe } from 'utils';

type Selectable = {
    value: string;
    name: string;
    label?: string;
    order?: number;
};

export type { Selectable };

/* eslint-disable no-redeclare */
function withValue<V>(selectable: Selectable, mapping: Mapping<Selectable, V>): V;
function withValue<V>(selectable: null | undefined, mapping: Mapping<Selectable, V>): undefined;
function withValue<V>(selectable: Maybe<Selectable>, mapping: Mapping<Selectable, V>) {
    return selectable ? mapping(selectable) : undefined;
}

/* eslint-disable no-redeclare */
function withValues<V>(selectables: undefined, mapping: Mapping<Selectable, V>): undefined;
function withValues<V>(selectables: Selectable[], mapping: Mapping<Selectable, V>): V[];
function withValues<V>(selectables: Selectable[] | undefined, mapping: Mapping<Selectable, V>) {
    return selectables && selectables.map((s) => withValue(s, mapping));
}

export { withValue, withValues };

/* eslint-disable no-redeclare */
function asNumericValue(selectable: Selectable): number;
function asNumericValue(selectable: null | undefined): undefined;
function asNumericValue(selectable: Maybe<Selectable>): undefined;
function asNumericValue(selectable: Maybe<Selectable>) {
    return selectable ? withValue(selectable, (s) => Number(s.value)) : undefined;
}

/* eslint-disable no-redeclare */
function asNumericValues(selectables: undefined): undefined;
function asNumericValues(selectables: Selectable[]): number[];
function asNumericValues(selectables: Selectable[] | undefined) {
    return selectables && withValues<number>(selectables, asNumericValue);
}

export { asNumericValue, asNumericValues };

/* eslint-disable no-redeclare */
function asValue(selectable: Selectable): string;
function asValue(selectable: null | undefined): undefined;
function asValue(selectable: Maybe<Selectable>): undefined;
function asValue(selectable: Maybe<Selectable>) {
    return selectable ? withValue(selectable, (s) => s?.value) : undefined;
}

/* eslint-disable no-redeclare */
function asValues(selectables: undefined): undefined;
function asValues(selectables: Selectable[]): string[];
function asValues(selectables: Selectable[] | undefined) {
    return selectables && withValues<string>(selectables, asValue);
}

export { asValue, asValues };

function asSelectable(value: string, name?: string): Selectable {
    return { name: name ?? value, label: name ?? value, value };
}

export { asSelectable };

const isEqual =
    (selectable: Selectable) =>
    (other: Selectable): boolean =>
        selectable.value === other.value;

export { isEqual };
