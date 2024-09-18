import { asValue } from 'options';
import { Address } from '../api';
import { AddressEntry } from '../entry';

const asAddress = (entry: AddressEntry): Address => {
    const { use, type, state, county, country, ...remaining } = entry;

    return {
        type: asValue(type),
        use: asValue(use),
        county: asValue(county),
        state: asValue(state),
        country: asValue(country),
        ...remaining
    };
};

export { asAddress };
