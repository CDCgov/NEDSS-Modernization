import { Supplier } from 'libs/supplying';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';

type NameDemographic = EffectiveDated & { sequence?: number } & Nullable<{
        type?: Selectable;
        prefix?: Selectable;
        first?: string;
        middle?: string;
        secondMiddle?: string;
        last?: string;
        secondLast?: string;
        suffix?: Selectable;
        degree?: Selectable;
    }>;

type HasNameDemographics = {
    names?: NameDemographic[];
};

export type { NameDemographic, HasNameDemographics };

const initial = (asOf: Supplier<string>): NameDemographic => ({
    asOf: asOf(),
    type: null,
    prefix: null,
    first: null,
    middle: null,
    secondMiddle: null,
    last: null,
    secondLast: null,
    suffix: null,
    degree: null
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
