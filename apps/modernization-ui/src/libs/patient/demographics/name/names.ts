import { Supplier } from 'libs/supplying';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type NameDemographic = EffectiveDated & {
    sequence?: number;
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

type HasNameDemographics = {
    names?: NameDemographic[];
};

export type { NameDemographic, HasNameDemographics };

const initial = (asOf: Supplier<string>): Partial<NameDemographic> => ({
    asOf: asOf(),
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

const labels = {
    asOf: 'As of',
    type: 'Type',
    prefix: 'Prefix',
    first: 'First',
    middle: 'Middle',
    secondMiddle: 'Second middle',
    last: 'Last',
    secondLast: 'Second last',
    suffix: 'Suffix',
    degree: 'Degree'
};

export { labels };
