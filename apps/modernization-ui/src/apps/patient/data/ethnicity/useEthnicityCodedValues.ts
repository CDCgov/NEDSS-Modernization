import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';

type EthnicityCodedValues = {
    ethnicGroups: Selectable[];
    ethnicityUnknownReasons: Selectable[];
    detailedEthnicities: Selectable[];
};

const useEthnicityCodedValues = (): EthnicityCodedValues => {
    const groups = useConceptOptions('PHVS_ETHNICITYGROUP_CDC_UNK', { lazy: false });
    const reasons = useConceptOptions('P_ETHN_UNK_REASON', { lazy: false });
    const detailed = useConceptOptions('P_ETHN', { lazy: false });

    return {
        ethnicGroups: groups.options,
        ethnicityUnknownReasons: reasons.options,
        detailedEthnicities: detailed.options
    };
};

export { useEthnicityCodedValues };
export type { EthnicityCodedValues };
