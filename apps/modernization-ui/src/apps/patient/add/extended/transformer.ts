import { asAdministrative, asName } from 'apps/patient/data';
import { ExtendedNewPatientEntry } from './entry';
import { Transformer } from './useAddExtendedPatient';
import { Mapping } from 'utils';

const maybeMap =
    <R, S>(mapping: Mapping<R, S>) =>
    (value?: R): S | undefined =>
        value ? mapping(value) : undefined;

const maybeMapAll =
    <R, S>(mapping: Mapping<R, S>) =>
    (value?: R[]): S[] =>
        value ? value.map(mapping) : [];

const asNames = maybeMapAll(asName);

const transformer: Transformer = (entry: ExtendedNewPatientEntry) => {
    const administrative = asAdministrative(entry.administrative);
    const names = asNames(entry.names);

    return { administrative, names };
};

export { transformer };
