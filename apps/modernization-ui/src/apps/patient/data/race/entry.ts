import { today } from 'date';
import { asSelectable, Selectable } from 'options';
import { EffectiveDated, Maybe } from 'utils';

type RaceEntry = EffectiveDated & {
    id: number;
    race: Maybe<Selectable>;
    detailed: Selectable[];
};

type RaceCategoryValidator = (id: number, category: Selectable) => Promise<string | boolean>;

export type { RaceEntry, RaceCategoryValidator };

const initial = (asOf: string = today()): Partial<RaceEntry> => ({
    id: new Date().getTime(),
    asOf,
    race: null,
    detailed: []
});

export { initial };
