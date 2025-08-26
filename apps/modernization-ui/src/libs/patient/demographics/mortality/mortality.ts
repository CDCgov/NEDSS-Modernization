import { Supplier } from 'libs/supplying';
import { Location } from 'libs/location';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';

type MortalityDemographic = EffectiveDated &
    Nullable<{
        asOf?: string;
        deceased?: Selectable;
        deceasedOn?: string;
    }> &
    Nullable<Location>;

type HasMortalityDemographic = {
    mortality?: MortalityDemographic;
};

export type { MortalityDemographic, HasMortalityDemographic };

const initial = (asOf: Supplier<string>): MortalityDemographic => ({
    asOf: asOf(),
    deceased: null,
    deceasedOn: null,
    city: null,
    state: null,
    county: null,
    country: null
});

export { initial };

const labels = {
    asOf: 'As of',
    deceased: 'Is the patient deceased?',
    deceasedOn: 'Date of death',
    city: 'Death city',
    state: 'Death state',
    county: 'Death county',
    country: 'Death country'
};

export { labels };
