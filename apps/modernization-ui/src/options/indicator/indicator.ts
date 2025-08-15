import { Selectable } from 'options/selectable';

const YES = { value: 'Y', name: 'Yes' };
const NO = { value: 'N', name: 'No' };
const UNKNOWN = { value: 'UNK', name: 'Unknown' };

type Indicators = {
    yes: Selectable;
    no: Selectable;
    unknown: Selectable;
    all: Selectable[];
};

const indicators: Indicators = {
    yes: YES,
    no: NO,
    unknown: UNKNOWN,
    all: [YES, NO, UNKNOWN]
};

export { indicators };
export type { Indicators };
