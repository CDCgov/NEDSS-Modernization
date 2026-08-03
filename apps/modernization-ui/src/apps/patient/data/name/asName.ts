import { asValue } from 'options';
import { exists, orUndefined } from 'utils';

import { Name } from '../api';

import { NameEntry } from './entry';

const asName = (entry: NameEntry): Name | undefined => {
    const { asOf, type, prefix, first, middle, secondMiddle, last, secondLast, suffix, degree } = entry;

    if (exists(type)) {
        return {
            asOf,
            type: asValue(type),
            prefix: asValue(prefix),
            first: orUndefined(first),
            middle: orUndefined(middle),
            secondMiddle: orUndefined(secondMiddle),
            last: orUndefined(last),
            secondLast: orUndefined(secondLast),
            suffix: asValue(suffix),
            degree: asValue(degree),
        };
    }
};
export { asName };
