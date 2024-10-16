import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated, Maybe } from 'utils';

type EthnicityEntry = EffectiveDated & {
    ethnicGroup: Maybe<Selectable>;
    detailed: Selectable[];
    unknownReason?: Selectable;
};

export type { EthnicityEntry };

const initial = (asOf: string = today()): EthnicityEntry => ({
    asOf: asOf,
    ethnicGroup: null,
    detailed: []
});

export { initial };
