import { CodedValue } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';

type NameCodedValues = {
    types: CodedValue[];
    prefixes: CodedValue[];
    suffixes: CodedValue[];
    degrees: CodedValue[];
};

const useNameCodedValues = (): NameCodedValues => {
    const types = useConceptOptions(ConceptNames.nameTypes, { lazy: false });
    const prefixes = useConceptOptions(ConceptNames.namePrefixes, { lazy: false });
    const suffixes = useConceptOptions(ConceptNames.nameSuffixes, { lazy: false });
    const degrees = useConceptOptions(ConceptNames.nameDegrees, { lazy: false });

    return {
        types: types.options,
        prefixes: prefixes.options,
        suffixes: suffixes.options,
        degrees: degrees.options
    };
};

export { useNameCodedValues };
export type { NameCodedValues };
