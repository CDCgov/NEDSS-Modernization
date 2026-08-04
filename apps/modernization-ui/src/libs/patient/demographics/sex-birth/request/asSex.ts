import { asValue } from 'options';
import { isEmpty, orUndefined } from 'utils';

import { SexDemographic } from '../sexBirth';

import { SexDemographicRequest } from './sexBirthRequest';

const asSex = (demographic: Partial<SexDemographic>): SexDemographicRequest | undefined => {
    const { asOf, ...remaining } = demographic;

    const { current, unknownReason, transgenderInformation, additionalGender } = remaining;

    if (asOf && !isEmpty(remaining)) {
        return {
            asOf,
            current: asValue(current),
            unknownReason: asValue(unknownReason),
            transgenderInformation: asValue(transgenderInformation),
            additionalGender: orUndefined(additionalGender),
        };
    }
};

export { asSex };
