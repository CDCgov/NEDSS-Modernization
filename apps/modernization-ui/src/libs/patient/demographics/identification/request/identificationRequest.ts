import { EffectiveDated } from 'utils';

type IdentificationDemographicRequest = EffectiveDated & {
    type: string;
    value: string;
    issuer?: string;
};

export type { IdentificationDemographicRequest };
