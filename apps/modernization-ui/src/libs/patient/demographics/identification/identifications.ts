import { Supplier } from 'libs/supplying';
import { Selectable } from 'options/selectable';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';

type IdentificationDemographic = EffectiveDated &
    Nullable<{
        sequence?: number;
        type: Selectable;
        issuer: Selectable;
        value: string;
    }>;

type HasIdentificationDemographics = {
    identifications?: IdentificationDemographic[];
};

export type { IdentificationDemographic, HasIdentificationDemographics };

const initial = (asOf: Supplier<string>): IdentificationDemographic => ({
    asOf: asOf(),
    type: null,
    issuer: null,
    value: null
});

export { initial };

const labels = {
    asOf: 'As of',
    type: 'Type',
    issuer: 'Assigning authority',
    value: 'ID value'
};

export { labels };
