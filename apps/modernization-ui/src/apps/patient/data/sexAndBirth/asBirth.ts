import { asValue } from 'options';
import { Birth } from '../api';
import { BirthEntry } from '../entry';

const asBirth = (entry: BirthEntry): Birth => {
    const { asOf, bornOn, sex, multiple, order, city, county, state, country } = entry;

    return {
        asOf,
        bornOn,
        sex: asValue(sex),
        multiple: asValue(multiple),
        order,
        city,
        county: asValue(county),
        state: asValue(state),
        country: asValue(country)
    };
};

export { asBirth };
