import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type NameDemographic = EffectiveDated & {
    identifier?: number;
    type: Selectable | null;
    prefix?: Selectable | null;
    first?: string;
    middle?: string;
    secondMiddle?: string;
    last?: string;
    secondLast?: string;
    suffix?: Selectable | null;
    degree?: Selectable | null;
};

export type { NameDemographic };

const initial = (asOf: string = today()): Partial<NameDemographic> => ({
    asOf,
    type: undefined,
    prefix: undefined,
    first: undefined,
    middle: undefined,
    secondMiddle: undefined,
    last: undefined,
    secondLast: undefined,
    suffix: undefined,
    degree: undefined
});

export { initial };
