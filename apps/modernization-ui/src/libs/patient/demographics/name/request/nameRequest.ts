import { EffectiveDated } from 'utils';

type NameDemographicRequest = EffectiveDated & {
    sequence?: number;
    type: string;
    prefix?: string;
    first?: string;
    middle?: string;
    secondMiddle?: string;
    last?: string;
    secondLast?: string;
    suffix?: string;
    degree?: string;
};

export type { NameDemographicRequest };
