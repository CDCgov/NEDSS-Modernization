type WithName = { name: string };
type WithLabel = { label: string };

type WithDisplay = WithName & WithLabel;

type Selectable = {
    value: string;
    order?: number;
} & WithDisplay;

export type { Selectable };

type HasValue = { value: string | number };

/* eslint-disable no-redeclare */
function asValue(selectable: HasValue): string;
function asValue(selectable: null | undefined): null;
function asValue(selectable: HasValue | null | undefined) {
    return selectable?.value || null;
}

/* eslint-disable no-redeclare */
function asValues(selectables: undefined): undefined;
function asValues(selectables: HasValue[]): string[];
function asValues(selectables: HasValue[] | undefined): string[] | undefined {
    return selectables && selectables.map((m) => asValue(m));
}
export { asValue, asValues };
