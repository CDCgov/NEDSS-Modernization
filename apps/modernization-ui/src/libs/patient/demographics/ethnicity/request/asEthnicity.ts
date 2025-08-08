import { asValue } from 'options';
import { exists } from 'utils';
import { EthnicityDemographic } from '../ethnicity';
import { EthnicityDemographicRequest } from './ethnicityRequest';
import { maybeMapAll } from 'utils/mapping';

const maybeAsValues = maybeMapAll(asValue);

const asEthnicity = (demographic: Partial<EthnicityDemographic>): EthnicityDemographicRequest | undefined => {
    const { ethnicGroup, detailed, unknownReason, asOf } = demographic;

    if (asOf && exists(ethnicGroup)) {
        return {
            asOf,
            ethnicGroup: asValue(ethnicGroup),
            detailed: maybeAsValues(detailed),
            unknownReason: asValue(unknownReason)
        };
    }
};

export { asEthnicity };
