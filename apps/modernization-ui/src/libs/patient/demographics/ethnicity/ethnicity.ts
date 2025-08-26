import { Supplier } from 'libs/supplying';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';

type EthnicityDemographic = EffectiveDated &
    Nullable<{
        ethnicGroup: Selectable;
        unknownReason?: Selectable;
        detailed: Selectable[];
    }>;

type HasEthnicityDemographic = {
    ethnicity?: EthnicityDemographic;
};

export type { EthnicityDemographic, HasEthnicityDemographic };

const initial = (asOf: Supplier<string>): EthnicityDemographic => ({
    asOf: asOf(),
    ethnicGroup: null,
    unknownReason: null,
    detailed: []
});

export { initial };

const labels = {
    asOf: 'As of',
    ethnicity: 'Ethnicity',
    detailed: 'Spanish origin',
    unknownReason: 'Reason unknown'
};

export { labels };
