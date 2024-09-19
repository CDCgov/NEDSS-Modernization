import { asValue, asValues } from 'options';
import { EthnicityEntry } from '../entry';
import { Ethnicity } from '../api';

const asEthnicity = (entry: EthnicityEntry): Ethnicity => {
    const { ethnicity, detailed, reasonUnknown, ...remaining } = entry;

    return {
        ethnicity: asValue(ethnicity),
        detailed: asValues(detailed),
        reasonUnknown: asValue(reasonUnknown),
        ...remaining
    };
};

export { asEthnicity };
