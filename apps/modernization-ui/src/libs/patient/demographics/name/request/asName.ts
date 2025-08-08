/* eslint-disable no-redeclare */
import { asValue } from 'options';
import { exists, orUndefined } from 'utils';
import { NameDemographic } from '../names';
import { NameDemographicRequest } from './nameRequest';

const asName = (demographic: Partial<NameDemographic>): NameDemographicRequest | undefined => {
    const { asOf, type, prefix, first, middle, secondMiddle, last, secondLast, suffix, degree } = demographic;

    if (asOf && exists(type)) {
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
