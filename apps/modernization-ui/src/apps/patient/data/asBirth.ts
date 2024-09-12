import { asValue } from 'options';
import { Birth } from './api';
import { BirthEntry } from './entry';

const asBirth = (entry: BirthEntry): Birth => {
    const { sex, multiple, county, state, country, ...remaining } = entry;

    return {
        sex: asValue(sex),
        multiple: asValue(multiple),
        county: asValue(county),
        state: asValue(state),
        country: asValue(country),
        ...remaining
    };
};

export { asBirth };
