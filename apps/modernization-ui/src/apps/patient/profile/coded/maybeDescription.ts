import { mapNonNull } from 'utils/mapNonNull';
import { ProfileCodedValue } from './ProfileCodedValue';

const maybeDescription = (coded?: ProfileCodedValue | null) => coded?.description ?? null;

const maybeDescriptions = (values?: (ProfileCodedValue | null)[]): string[] => mapNonNull(maybeDescription, values);

export { maybeDescription, maybeDescriptions };
