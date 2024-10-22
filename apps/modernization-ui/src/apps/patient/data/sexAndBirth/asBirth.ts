import { asValue } from 'options';
import { Birth } from '../api';
import { BirthEntry } from '../entry';
import { isEmpty, orUndefined } from 'utils';

const asBirth = (entry: BirthEntry): Birth | undefined => {
    const { asOf, ...remaining } = entry;

    if (!isEmpty(remaining)) {
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
