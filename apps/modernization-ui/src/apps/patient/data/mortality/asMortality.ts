import { asValue } from 'options';
import { Mortality } from '../api';
import { MortalityEntry } from '../entry';

const asMortality = (entry: MortalityEntry): Mortality => {
    const { deceased, county, state, country, ...remaining } = entry;

    return {
        deceased: asValue(deceased),
        county: asValue(county),
        state: asValue(state),
        country: asValue(country),
        ...remaining
    };
};

export { asMortality };
