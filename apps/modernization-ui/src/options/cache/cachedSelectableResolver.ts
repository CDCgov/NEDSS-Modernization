import { addDays } from 'date-fns';
import { Selectable } from '../selectable';
import { selectableResolver } from '../selectableResolver';
import { cache } from './cached';

const expiration = () => addDays(new Date(), 1);

const cached = (id: string) => cache<Selectable[]>({ id, expiration, storage: localStorage });

const cachedSelectableResolver = (id: string, url: string) => () => cached(id)(() => selectableResolver(url));

export { cachedSelectableResolver };
