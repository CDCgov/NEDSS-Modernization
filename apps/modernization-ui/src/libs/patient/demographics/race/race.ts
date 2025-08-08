import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type RaceDemographic = EffectiveDated & {
    id: number;
    race: Selectable;
    detailed: Array<Selectable>;
};

type HasRaceDemographics = {
    races?: RaceDemographic[];
};

export type { RaceDemographic, HasRaceDemographics };

const initial = (asOf: string = today()): Partial<RaceDemographic> => ({
    id: new Date().getTime(),
    asOf,
    race: undefined,
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
