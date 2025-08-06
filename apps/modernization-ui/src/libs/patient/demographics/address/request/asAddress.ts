import { asValue } from 'options';
import { exists, orUndefined } from 'utils';
import { AddressDemographic } from '../address';
import { AddressDemographicRequest } from './addressRequest';

const asAddress = (demographic: Partial<AddressDemographic>): AddressDemographicRequest | undefined => {
    const { asOf, use, type, state, county, country, address1, address2, city, zipcode, censusTract, comment } =
        demographic;

    if (asOf && exists(use) && exists(type)) {
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
