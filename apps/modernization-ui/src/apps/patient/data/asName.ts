/* eslint-disable no-redeclare */
import { asValue } from 'options';
import { Name } from './api';
import { NameEntry } from './entry';

const asName = (entry: NameEntry): Name => {
    const { asOf, type, prefix, first, middle, secondMiddle, last, secondLast, suffix, degree } = entry;

    return {
        asOf,
        type: asValue(type),
        prefix: asValue(prefix),
        first,
        middle,
        secondMiddle,
        last,
        secondLast,
        suffix: asValue(suffix),
        degree: asValue(degree)
    };
};
export { asName };
