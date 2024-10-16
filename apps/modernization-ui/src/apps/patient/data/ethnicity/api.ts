import { EffectiveDated } from 'utils';

type Ethnicity = EffectiveDated & {
    ethnicGroup: string;
    detailed: string[];
    unknownReason?: string;
};

export type { Ethnicity };
