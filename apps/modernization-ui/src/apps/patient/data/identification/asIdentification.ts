import { asValue } from 'options';
import { Identification } from '../api';
import { IdentificationEntry } from '../entry';

const asIdentification = (entry: IdentificationEntry): Identification => {
    const { type, issuer, ...remaining } = entry;

    return {
        type: asValue(type),
        issuer: asValue(issuer),
        ...remaining
    };
};

export { asIdentification };
