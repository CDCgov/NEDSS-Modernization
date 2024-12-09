import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type NameEntry = EffectiveDated & {
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

export type { NameEntry };

const initial = (asOf: string = today()): Partial<NameEntry> => ({
    asOf,
    type: undefined,
    prefix: undefined,
    first: '',
    middle: '',
    secondMiddle: '',
    last: '',
    secondLast: '',
    suffix: undefined,
    degree: undefined
});

export { initial };
