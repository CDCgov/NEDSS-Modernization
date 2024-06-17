type WithName = { name: string };
type WithLabel = { label: string };

type WithDisplay = WithName & WithLabel;

type Selectable = {
    value: string;
    order?: number;
} & WithDisplay;

export type { Selectable };

type HasValue = { value: string | number; name: string };

function asValue(selectable: HasValue | null | undefined): string | number | null {
    return selectable?.value || null;
}

function asValues(selectables: HasValue[] | undefined): (string | number | null)[] | undefined {
    return selectables && selectables.map((m) => asValue(m));
}
export { asValue, asValues };

const isEqual =
    (selectable: Selectable) =>
    (other: Selectable): boolean =>
        selectable.value === other.value;

export { isEqual };
