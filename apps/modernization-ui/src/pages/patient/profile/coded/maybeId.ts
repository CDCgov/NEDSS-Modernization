import { mapNonNull } from 'utils/mapNonNull';
import { ProfileCodedValue } from './ProfileCodedValue';

const maybeId = (coded?: ProfileCodedValue | null) => coded?.id ?? null;

const maybeIds = (values?: (ProfileCodedValue | null)[]): string[] => mapNonNull(maybeId, values);

export { maybeId, maybeIds };
