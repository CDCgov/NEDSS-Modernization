import { today } from 'date';
import { Selectable } from 'options/selectable';

type IdentificationDemographic = {
    asOf: string;
    type?: Selectable;
    issuer?: Selectable;
    value?: string;
};

export type { IdentificationDemographic };

const initial = (asOf: string = today()): Partial<IdentificationDemographic> => ({
    asOf,
    type: undefined,
    issuer: undefined,
    value: undefined
});

export { initial };
