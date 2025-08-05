import { EffectiveDated } from 'utils';

type EthnicityDemographicRequest = EffectiveDated & {
    ethnicGroup: string;
    detailed: string[];
    unknownReason?: string;
};

export type { EthnicityDemographicRequest };
