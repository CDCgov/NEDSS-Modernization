import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type RaceDemographic = EffectiveDated & {
    race: Selectable;
    detailed: Array<Selectable>;
};

export type { RaceDemographic };

const initial = (asOf: string = today()): Partial<RaceDemographic> => ({
    asOf,
    race: undefined,
    detailed: []
});

export { initial };
