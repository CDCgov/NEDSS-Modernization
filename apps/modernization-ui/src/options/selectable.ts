type WithName = { name: string };
type WithLabel = { label: string };

type WithDisplay = WithName & WithLabel;

type Selectable = {
    value: string;
    order?: number;
} & WithDisplay;

export type { Selectable };

/* eslint-disable no-redeclare */
function asValue(selectable: Selectable): string;
function asValue(selectable: null | undefined): undefined;
function asValue(selectable: Selectable | null | undefined): undefined;
function asValue(selectable: Selectable | null | undefined) {
    return selectable?.value || undefined;
}

/* eslint-disable no-redeclare */
function asValues(selectables: undefined): undefined;
function asValues(selectables: Selectable[]): string[];
function asValues(selectables: Selectable[] | undefined): string[] | undefined {
    return selectables && selectables.map((m) => asValue(m));
}
export { asValue, asValues };

function asSelectable(value: string, name?: string): Selectable {
    return { name: name ?? value, label: name ?? value, value };
}

export { asSelectable };
