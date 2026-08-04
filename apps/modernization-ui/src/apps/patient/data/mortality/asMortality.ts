import { asValue } from 'options';
import { isEmpty, orUndefined } from 'utils';

import { Mortality } from '../api';
import { MortalityEntry } from '../entry';

const asMortality = (entry: MortalityEntry): Mortality | undefined => {
    const { asOf, ...remaining } = entry;

    if (!isEmpty(remaining)) {
        const { deceasedOn, deceased, county, state, country, city } = remaining;

        return {
            asOf,
            deceasedOn: orUndefined(deceasedOn),
            deceased: asValue(deceased),
            city: orUndefined(city),
            county: asValue(county),
            state: asValue(state),
            country: asValue(country),
        };
    }
};

export { asMortality };
