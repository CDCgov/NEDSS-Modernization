import { EffectiveDated } from 'utils';

type SexDemographicRequest = EffectiveDated & {
    current?: string;
    unknownReason?: string;
    transgenderInformation?: string;
    additionalGender?: string;
};

type BirthDemographicRequest = EffectiveDated & {
    bornOn?: string;
    sex?: string;
    multiple?: string;
    order?: number;
    city?: string;
    state?: string;
    county?: string;
    country?: string;
};

export type { SexDemographicRequest, BirthDemographicRequest };
