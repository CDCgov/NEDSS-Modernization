import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type IdentificationEntry = EffectiveDated & {
    type: Selectable | null;
    id: string | null;
    issuer?: Selectable | null;
};

export type { IdentificationEntry };

const initial = (asOf: string = today()): Partial<IdentificationEntry> => ({
    asOf,
    type: undefined,
    issuer: undefined,
    id: ''
});

export { initial };
