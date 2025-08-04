import { parseISO } from 'date-fns';
import { maybeMap } from 'utils/mapping';

const maybeDate = maybeMap((value: string) => new Date(parseISO(value)));

export { maybeDate };
