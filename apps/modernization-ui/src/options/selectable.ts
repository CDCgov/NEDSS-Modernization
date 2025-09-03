import { Mapping } from 'utils';

type Selectable = {
    value: string;
    name: string;
    label?: string;
};

export type { Selectable };

/* eslint-disable no-redeclare */
function mapExisting<V>(mapping: Mapping<Selectable, V>, selectable: Selectable): V;
function mapExisting<V>(mapping: Mapping<Selectable, V>, selectable?: null): undefined;
function mapExisting<V>(mapping: Mapping<Selectable, V>, selectable?: Selectable | null) {
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
function asNumericValue(selectable?: null): undefined;
function asNumericValue(selectable?: Selectable | null) {
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
function asValue(selectable?: null | undefined): undefined;
function asValue(selectable?: Selectable | null): undefined;
function asValue(selectable?: Selectable | null) {
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
function asName(selectable?: null): undefined;
function asName(selectable?: Selectable | null): undefined;
function asName(selectable?: Selectable | null) {
    return selectable ? mapExisting((s) => s?.name, selectable) : undefined;
}

export { asName };

const asSelectable = (value: string, name?: string, label?: string): Selectable => ({
    value,
    name: name ?? value,
    label
});

export { asSelectable };

const isEqual =
    (selectable: Selectable) =>
    (other?: Selectable | null): boolean =>
        selectable.value === other?.value;

export { isEqual };
