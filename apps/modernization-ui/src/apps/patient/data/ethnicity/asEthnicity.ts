import { asValue, asValues } from 'options';
import { exists } from 'utils';

import { Ethnicity } from './api';
import { EthnicityEntry } from './entry';

const asEthnicity = (entry: EthnicityEntry): Ethnicity | undefined => {
    const { ethnicGroup, detailed, unknownReason, asOf } = entry;

    if (exists(ethnicGroup)) {
        return {
            asOf,
            ethnicGroup: asValue(ethnicGroup),
            detailed: asValues(detailed),
            unknownReason: asValue(unknownReason),
        };
    }
};

export { asEthnicity };
