import { asValue } from 'options';
import { Identification } from '../api';
import { IdentificationEntry } from '../entry';
import { exists } from 'utils';

const asIdentification = (entry: IdentificationEntry): Identification | undefined => {
    const { asOf, type, issuer, id } = entry;

    if (exists(type)) {
        return {
            asOf,
            type: asValue(type),
            id,
            issuer: asValue(issuer)
        };
    }
};

export { asIdentification };
