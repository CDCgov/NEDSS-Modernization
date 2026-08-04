import { asValue } from 'options';
import { exists } from 'utils';

import { IdentificationDemographic } from '../identifications';

import { IdentificationDemographicRequest } from './identificationRequest';

const asIdentification = (
    demographic: Partial<IdentificationDemographic>
): IdentificationDemographicRequest | undefined => {
    const { sequence, asOf, type, issuer, value } = demographic;

    if (asOf && exists(type) && value) {
        return {
            sequence,
            asOf,
            type: asValue(type),
            value,
            issuer: asValue(issuer),
        };
    }
};

export { asIdentification };
