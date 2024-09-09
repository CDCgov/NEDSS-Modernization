import { ExtendedNewPatientEntry } from './entry';
import { Transformer } from './useAddExtendedPatient';

const transformer: Transformer = (entry: ExtendedNewPatientEntry) => {
    const { administrative } = entry;

    return { administrative };
};

export { transformer };
