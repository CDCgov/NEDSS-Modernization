import { maybeMap } from 'utils/mapping';

const maybeDate = maybeMap((value: string) => new Date(value));

export { maybeDate };
