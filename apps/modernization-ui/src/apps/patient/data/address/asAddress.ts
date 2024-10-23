import { asValue } from 'options';
import { Address } from '../api';
import { AddressEntry } from '../entry';
import { exists, orUndefined } from 'utils';

const asAddress = (entry: AddressEntry): Address | undefined => {
    const { asOf, use, type, state, county, country, address1, address2, city, zipcode, censusTract, comment } = entry;

    if (asOf && use && exists(use) && type && exists(type)) {
        return {
            asOf,
            type: asValue(type),
            use: asValue(use),
            county: asValue(county),
            state: asValue(state),
            country: asValue(country),
            address1: orUndefined(address1),
            address2: orUndefined(address2),
            city: orUndefined(city),
            zipcode: orUndefined(zipcode),
            censusTract: orUndefined(censusTract),
            comment: orUndefined(comment)
        };
    }
};

export { asAddress };
