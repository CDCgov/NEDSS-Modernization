import { Supplier } from 'libs/supplying';
import { Location } from 'libs/location';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';

type SexDemographic = EffectiveDated &
    Nullable<{
        current?: Selectable;
        unknownReason?: Selectable;
        transgenderInformation?: Selectable;
        additionalGender?: string;
    }>;

type BirthDemographic = EffectiveDated &
    Nullable<{
        bornOn?: string;
        sex?: Selectable;
        multiple?: Selectable;
        order?: number;
    }> &
    Nullable<Location>;

type SexBirthDemographic = SexDemographic & BirthDemographic;

type HasSexBirthDemographic = {
    sexBirth?: SexBirthDemographic;
};

export type { SexBirthDemographic, BirthDemographic, SexDemographic, HasSexBirthDemographic };

const initial = (asOf: Supplier<string>): SexBirthDemographic => ({
    asOf: asOf(),
    current: null,
    unknownReason: null,
    transgenderInformation: null,
    additionalGender: null,
    bornOn: null,
    city: null,
    state: null,
    county: null,
    country: null,
    sex: null,
    multiple: null,
    order: null
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
