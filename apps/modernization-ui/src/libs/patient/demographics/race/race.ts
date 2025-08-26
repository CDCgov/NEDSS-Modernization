import { Supplier } from 'libs/supplying';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';

type RaceDemographic = EffectiveDated &
    Nullable<{
        id: number;
        race: Selectable;
        detailed: Selectable[];
    }>;

type HasRaceDemographics = {
    races?: RaceDemographic[];
};

export type { RaceDemographic, HasRaceDemographics };

const initial = (asOf: Supplier<string>): RaceDemographic => ({
    id: new Date().getTime(),
    asOf: asOf(),
    race: null,
    detailed: []
});

export { initial };

const labels = {
    asOf: 'As of',
    race: 'Race',
    detailed: 'Detailed race'
};

export { labels };

type RaceCategoryValidator = (id: number, category: Selectable | null) => Promise<string | boolean>;

export type { RaceCategoryValidator };
