import { Selectable } from './selectable';

const findByValue = (selectables: Selectable[]) => (value: string) =>
    selectables.find((selectable) => selectable.value === value);

export { findByValue };
