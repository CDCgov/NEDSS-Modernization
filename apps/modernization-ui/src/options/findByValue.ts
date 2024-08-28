import { Selectable } from './selectable';

type SelectableResolver = (id: string) => Selectable | undefined;

const findByValue =
    (selectables: Selectable[], defaultValue?: Selectable): SelectableResolver =>
    (value: string) =>
        selectables.find((selectable) => selectable.value === value) ?? defaultValue;

export { findByValue };

export type { SelectableResolver };
