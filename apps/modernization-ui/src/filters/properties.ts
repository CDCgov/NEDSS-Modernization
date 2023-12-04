import { Selectable } from 'options';

type BaseProperty = {
    name: string;
    value: string;
};

type Complete = (criteria: string) => Promise<Selectable[]>;

type ValueProperty = BaseProperty & {
    type: 'value';
    complete?: Complete;
    all?: Selectable[];
};

type DateProperty = BaseProperty & {
    type: 'date';
};

type PropertyTypes = 'value' | 'date';
type Property = ValueProperty | DateProperty;

export type { Property, PropertyTypes, ValueProperty, DateProperty };
