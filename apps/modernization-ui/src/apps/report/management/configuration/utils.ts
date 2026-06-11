import { Selectable } from 'options';

export const addLabelToName = ({ value, name, label }: Selectable) => ({
    value,
    label,
    name: !label || label === name ? name : `${name} (${label})`,
});

export interface EnumSelectable<T> {
    value: T;
    name: string;
}
