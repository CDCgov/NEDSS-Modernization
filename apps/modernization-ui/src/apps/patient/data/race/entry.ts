import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type RaceEntry = EffectiveDated & {
    id: number;
    race: Selectable | null;
    detailed: Selectable[];
};

type RaceCategoryValidator = (id: number, category: Selectable | null) => Promise<string | boolean>;

export type { RaceEntry, RaceCategoryValidator };

const initial = (asOf: string = today()): Partial<RaceEntry> => ({
    id: new Date().getTime(),
    asOf,
    race: undefined,
    detailed: []
});

export { initial };
