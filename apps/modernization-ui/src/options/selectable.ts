import { Mapping, Maybe } from 'utils';

type Selectable = {
    value: string;
    name: string;
    label?: string;
    order?: number;
};

export type { Selectable };

/* eslint-disable no-redeclare */
function mapExisting<V>(mapping: Mapping<Selectable, V>, selectable: Selectable): V;
function mapExisting<V>(mapping: Mapping<Selectable, V>, selectable: null | undefined): undefined;
function mapExisting<V>(mapping: Mapping<Selectable, V>, selectable: Maybe<Selectable>) {
    return selectable ? mapping(selectable) : undefined;
}

/* eslint-disable no-redeclare */
function mapAllExisting<V>(items: undefined, mapping: Mapping<Selectable, V>): undefined;
function mapAllExisting<V>(items: Selectable[], mapping: Mapping<Selectable, V>): V[];
function mapAllExisting<V>(items: Selectable[] | undefined, mapping: Mapping<Selectable, V>) {
    return items && items.map((s) => mapExisting(mapping, s));
}

/* eslint-disable no-redeclare */
function asNumericValue(selectable: Selectable): number;
function asNumericValue(selectable: null | undefined): undefined;
function asNumericValue(selectable: Maybe<Selectable>): undefined;
function asNumericValue(selectable: Maybe<Selectable>) {
    return selectable ? mapExisting((s) => Number(s.value), selectable) : undefined;
}

/* eslint-disable no-redeclare */
function asNumericValues(items: undefined): undefined;
function asNumericValues(items: Selectable[]): number[];
function asNumericValues(items: Selectable[] | undefined) {
    return items && mapAllExisting<number>(items, asNumericValue);
}

export { asNumericValue, asNumericValues };

/* eslint-disable no-redeclare */
function asValue(selectable: Selectable): string;
function asValue(selectable: null | undefined): undefined;
function asValue(selectable: Maybe<Selectable>): undefined;
function asValue(selectable: Maybe<Selectable>) {
    return selectable ? mapExisting((s) => s?.value, selectable) : undefined;
}

/* eslint-disable no-redeclare */
function asValues(items: undefined): undefined;
function asValues(items: Selectable[]): string[];
function asValues(items: Selectable[] | undefined) {
    return items && mapAllExisting<string>(items, asValue);
}

export { asValue, asValues };

/* eslint-disable no-redeclare */
function asName(selectable: Selectable): string;
function asName(selectable: null | undefined): undefined;
function asName(selectable: Maybe<Selectable>): undefined;
function asName(selectable: Maybe<Selectable>) {
    return selectable ? mapExisting((s) => s?.name, selectable) : undefined;
}

export { asName };

const asSelectable = (value: string, name?: string): Selectable => ({
    name: name ?? value,
    label: name ?? value,
    value
});

export { asSelectable };

const isEqual =
    (selectable: Selectable) =>
    (other: Selectable): boolean =>
        selectable.value === other.value;

export { isEqual };
