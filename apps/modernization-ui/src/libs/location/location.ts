import { Selectable } from 'options';

type Location = {
    city?: string;
    state?: Selectable;
    county?: Selectable;
    country?: Selectable;
};

export type { Location };
