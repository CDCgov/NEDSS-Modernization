import { asValue, asValues } from 'options';
import { EthnicityEntry } from './entry';
import { Ethnicity } from './api';

const asEthnicity = (entry: EthnicityEntry): Ethnicity => {
    const { ethnicity, detailed, ...remaining } = entry;

    return {
        ethnicity: asValue(ethnicity),
        detailed: asValues(detailed),
        ...remaining
    };
};

export { asEthnicity };
