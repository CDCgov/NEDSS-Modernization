import { Selectable } from 'options';
import { Location } from 'libs/location';
import { today } from 'date';

type MortalityDemographic = Location & {
    asOf?: string;
    deceased?: Selectable;
    deceasedOn?: string;
};

type HasMortalityDemographic = {
    mortality?: MortalityDemographic;
};

export type { MortalityDemographic, HasMortalityDemographic };

const initial = (asOf: string = today()): MortalityDemographic => ({
    asOf,
    deceased: undefined,
    deceasedOn: undefined,
    city: undefined,
    state: undefined,
    county: undefined,
    country: undefined
});

export { initial };

const labels = {
    asOf: 'As of',
    deceased: 'Is the patient deceased?',
    deceasedOn: 'Date of death',
    city: 'City of death',
    state: 'State of death',
    county: 'County of death',
    country: 'Country of death'
};

export { labels };
