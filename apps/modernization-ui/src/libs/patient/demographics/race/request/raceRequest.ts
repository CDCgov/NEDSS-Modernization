import { EffectiveDated } from 'utils';

type RaceDemographicRequest = EffectiveDated & {
    race: string;
    detailed: string[];
};

export type { RaceDemographicRequest };
