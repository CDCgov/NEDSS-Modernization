import { Selectable } from 'options';

export const formatLabelName = (name: string, label?: string): string =>
    !label || label === name ? name : `${name} (${label})`;

export const addLabelToName = ({ value, name, label }: Selectable) => ({
    value,
    label,
    name: formatLabelName(name, label),
});

export interface EnumSelectable<T> {
    value: T;
    name: string;
}
