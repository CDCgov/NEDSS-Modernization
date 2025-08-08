import { asValue } from 'options';
import { isEmpty, orUndefined } from 'utils';
import { MortalityDemographic } from '../mortality';
import { MortalityDemographicRequest } from './mortalityRequest';

const asMortality = (demographic: Partial<MortalityDemographic>): MortalityDemographicRequest | undefined => {
    const { asOf, ...remaining } = demographic;

    if (asOf && !isEmpty(remaining)) {
        const { deceasedOn, deceased, county, state, country, city } = remaining;

        return {
            asOf,
            deceasedOn: orUndefined(deceasedOn),
            deceased: asValue(deceased),
            city: orUndefined(city),
            county: asValue(county),
            state: asValue(state),
            country: asValue(country)
        };
    }
};

export { asMortality };
