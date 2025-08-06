import { asValue } from 'options';
import { isEmpty, orUndefined } from 'utils';
import { BirthDemographicRequest } from './sexBirthRequest';
import { BirthDemographic } from '../sexBirth';

const asBirth = (entry: Partial<BirthDemographic>): BirthDemographicRequest | undefined => {
    const { asOf, ...remaining } = entry;

    if (asOf && !isEmpty(remaining)) {
        const { bornOn, sex, multiple, order, city, county, state, country } = remaining;

        return {
            asOf,
            bornOn: orUndefined(bornOn),
            sex: asValue(sex),
            multiple: asValue(multiple),
            order: orUndefined(order),
            city: orUndefined(city),
            county: asValue(county),
            state: asValue(state),
            country: asValue(country)
        };
    }
};

export { asBirth };
