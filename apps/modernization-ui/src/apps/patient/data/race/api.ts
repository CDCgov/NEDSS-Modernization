import { EffectiveDated } from 'utils';

type Race = EffectiveDated & {
    race: string;
    detailed: string[];
};

export type { Race };
