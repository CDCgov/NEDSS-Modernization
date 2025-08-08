import { today } from 'date';
import { Selectable } from 'options/selectable';

type IdentificationDemographic = {
    sequence?: number;
    asOf: string;
    type?: Selectable;
    issuer?: Selectable;
    value?: string;
};

type HasIdentificationDemographics = {
    identifications?: IdentificationDemographic[];
};

export type { IdentificationDemographic, HasIdentificationDemographics };

const initial = (asOf: string = today()): Partial<IdentificationDemographic> => ({
    asOf,
    type: undefined,
    issuer: undefined,
    value: undefined
});

export { initial };

const labels = {
    asOf: 'As of',
    type: 'Type',
    issuer: 'Assigning authority',
    value: 'ID value'
};

export { labels };
