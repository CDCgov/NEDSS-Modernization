import { CodedValue } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';

type EthnicityCodedValues = {
    ethnicGroups: CodedValue[];
    ethnicityUnknownReasons: CodedValue[];
    detailedEthnicities: CodedValue[];
};

const useEthnicityCodedValues = (): EthnicityCodedValues => {
    const groups = useConceptOptions(ConceptNames.ethnicGroups, { lazy: false });
    const reasons = useConceptOptions(ConceptNames.ethnicityUnknownReasons, { lazy: false });
    const detailed = useConceptOptions(ConceptNames.detailedEthnicities, { lazy: false });

    return {
        ethnicGroups: groups.options,
        ethnicityUnknownReasons: reasons.options,
        detailedEthnicities: detailed.options
    };
};

export { useEthnicityCodedValues };
export type { EthnicityCodedValues };
