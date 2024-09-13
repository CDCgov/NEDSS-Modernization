/* eslint-disable no-redeclare */
import { asValue } from 'options';
import { Name } from './api';
import { NameEntry } from './entry';

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
