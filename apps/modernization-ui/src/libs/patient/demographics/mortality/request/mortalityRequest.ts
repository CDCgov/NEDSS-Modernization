import { EffectiveDated } from 'utils';

type MortalityDemographicRequest = EffectiveDated & {
    deceased?: string;
    deceasedOn?: string;
    city?: string;
    state?: string;
    county?: string;
    country?: string;
};

export type { MortalityDemographicRequest };
