import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type RaceEntry = EffectiveDated & {
    race: Selectable;
    detailed: Selectable[];
};

export type { RaceEntry };

const initial = (asOf: string = today()): Partial<RaceEntry> => ({
    asOf,
    race: undefined,
    detailed: []
});

export { initial };
