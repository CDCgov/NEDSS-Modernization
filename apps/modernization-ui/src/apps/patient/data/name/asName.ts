import { asValue } from 'options';
import { NameEntry } from './entry';
import { Name } from '../api';
import { exists, orUndefined } from 'utils';

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
            degree: asValue(degree)
        };
    }
};
export { asName };
