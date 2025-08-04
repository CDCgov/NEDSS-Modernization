import { parseISO } from 'date-fns';
import { maybeMap } from 'utils/mapping';

const maybeDate = maybeMap((value: string) => parseISO(value));

export { maybeDate };
