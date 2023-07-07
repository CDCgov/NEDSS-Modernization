import { PatientCodedValue } from 'generated/graphql/schema';

import { mapNonNull } from 'utils/mapNonNull';

const maybeDescription = (coded?: PatientCodedValue | null) => coded?.description ?? null;

const maybeDescriptions = (values?: (PatientCodedValue | null)[]): string[] => mapNonNull(maybeDescription, values);

export { maybeDescription, maybeDescriptions };
