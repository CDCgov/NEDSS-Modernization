import { asValue } from 'options';
import { exists } from 'utils';
import { maybeMapAll } from 'utils/mapping';

import { EthnicityDemographic } from '../ethnicity';

import { EthnicityDemographicRequest } from './ethnicityRequest';

const maybeAsValues = maybeMapAll(asValue);

const asEthnicity = (demographic: Partial<EthnicityDemographic>): EthnicityDemographicRequest | undefined => {
    const { ethnicGroup, detailed, unknownReason, asOf } = demographic;

    if (asOf && exists(ethnicGroup)) {
        return {
            asOf,
            ethnicGroup: asValue(ethnicGroup),
            detailed: maybeAsValues(detailed),
            unknownReason: asValue(unknownReason),
        };
    }
};

export { asEthnicity };
