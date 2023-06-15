import { PatientCodedValue } from 'generated/graphql/schema';
import { mapNonNull } from 'utils/mapNonNull';

const maybeId = (coded?: PatientCodedValue | null) => coded?.id ?? null;

const maybeIds = (values?: (PatientCodedValue | null)[]): string[] => mapNonNull(maybeId, values);

export { maybeId, maybeIds };
