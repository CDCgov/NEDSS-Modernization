import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';
import { Location } from 'libs/location';

type SexDemographic = EffectiveDated & {
    current?: Selectable;
    unknownReason?: Selectable;
    transgenderInformation?: Selectable;
    additionalGender?: string;
};

type BirthDemographic = EffectiveDated &
    Location & {
        bornOn?: string;
        sex?: Selectable;
        multiple?: Selectable;
        order?: number;
    };

type SexBirthDemographic = SexDemographic & BirthDemographic;

export type { SexBirthDemographic };

const initial = (asOf: string = today()): SexBirthDemographic => ({
    asOf,
    current: undefined,
    unknownReason: undefined,
    transgenderInformation: undefined,
    additionalGender: undefined,
    bornOn: undefined,
    city: undefined,
    state: undefined,
    county: undefined,
    country: undefined,
    sex: undefined,
    multiple: undefined,
    order: undefined
});

export { initial };

const labels = {
    asOf: 'As of',
    bornOn: 'Date of birth',
    age: 'Current age',
    current: 'Current sex',
    unknownReason: 'Unknown reason',
    transgenderInformation: 'Transgender information',
    additionalGender: 'Additional gender',
    sex: 'Birth sex',
    multiple: 'Multiple birth',
    order: 'Birth order',
    city: 'Birth city',
    state: 'Birth state',
    county: 'Birth county',
    country: 'Birth country'
};

export { labels };
