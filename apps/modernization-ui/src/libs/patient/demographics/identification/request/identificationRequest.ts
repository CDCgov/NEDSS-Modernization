import { EffectiveDated } from 'utils';

type IdentificationDemographicRequest = EffectiveDated & {
    sequence?: number;
    type: string;
    value: string;
    issuer?: string;
};

export type { IdentificationDemographicRequest };
