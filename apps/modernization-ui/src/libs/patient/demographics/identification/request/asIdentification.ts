import { asValue } from 'options';
import { exists } from 'utils';
import { IdentificationDemographicRequest } from './identificationRequest';
import { IdentificationDemographic } from '../identifications';

const asIdentification = (
    demographic: Partial<IdentificationDemographic>
): IdentificationDemographicRequest | undefined => {
    const { asOf, type, issuer, value } = demographic;

    if (asOf && exists(type) && value) {
        return {
            asOf,
            type: asValue(type),
            value,
            issuer: asValue(issuer)
        };
    }
};

export { asIdentification };
