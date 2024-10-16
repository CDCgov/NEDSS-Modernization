import { asValue, asValues } from 'options';
import { EthnicityEntry } from './entry';
import { Ethnicity } from './api';

const asEthnicity = (entry: EthnicityEntry): Ethnicity | undefined => {
    const { ethnicGroup, detailed, unknownReason, asOf } = entry;

    if (ethnicGroup) {
        return {
            asOf,
            ethnicGroup: asValue(ethnicGroup),
            detailed: asValues(detailed),
            unknownReason: asValue(unknownReason)
        };
    }
};

export { asEthnicity };
