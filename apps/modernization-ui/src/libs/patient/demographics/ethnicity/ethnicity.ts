import { Selectable } from 'options';

type EthnicityDemographic = {
    asOf?: string;
    ethnicGroup?: Selectable;
    unknownReason?: Selectable;
    detailed?: Selectable[];
};

export type { EthnicityDemographic };
