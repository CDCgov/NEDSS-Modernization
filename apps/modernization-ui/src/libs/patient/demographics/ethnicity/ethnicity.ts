import { today } from 'date';
import { Selectable } from 'options';

type EthnicityDemographic = {
    asOf?: string;
    ethnicGroup?: Selectable;
    unknownReason?: Selectable;
    detailed?: Selectable[];
};

type HasEthnicityDemographic = {
    ethnicity?: EthnicityDemographic;
};

export type { EthnicityDemographic, HasEthnicityDemographic };

const initial = (asOf: string = today()): EthnicityDemographic => ({
    asOf,
    ethnicGroup: undefined,
    unknownReason: undefined,
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
