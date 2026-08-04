import { asValue } from 'options';
import { exists } from 'utils';

import { Identification } from '../api';

import { IdentificationEntry } from './entry';

const asIdentification = (entry: IdentificationEntry): Identification | undefined => {
    const { asOf, type, issuer, id } = entry;

    if (exists(type) && id) {
        return {
            asOf,
            type: asValue(type),
            value: id,
            issuer: asValue(issuer),
        };
    }
};

export { asIdentification };
