/* eslint-disable no-redeclare */
import { asValue } from 'options';
import { NameEntry } from '../entry';
import { Name } from '../api';

const asName = (entry: NameEntry): Name => {
    const { type, prefix, suffix, degree, ...remaining } = entry;

    return {
        ...remaining,
        type: asValue(type),
        prefix: asValue(prefix),
        suffix: asValue(suffix),
        degree: asValue(degree)
    };
};
export { asName };
