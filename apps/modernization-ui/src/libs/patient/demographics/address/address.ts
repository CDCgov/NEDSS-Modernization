import { Supplier } from 'libs/supplying';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type AddressDemographic = EffectiveDated & {
    identifier?: number;
    type: Selectable;
    use: Selectable;
    address1?: string;
    address2?: string;
    city?: string;
    state?: Selectable;
    zipcode?: string;
    county?: Selectable;
    censusTract?: string;
    country?: Selectable;
    comment?: string;
};

type HasAddressDemographics = {
    addresses?: AddressDemographic[];
};

export type { AddressDemographic, HasAddressDemographics };

type AddressDemographicDefaults = {
    state?: Selectable;
    country?: Selectable;
};

const initial = (asOf: Supplier<string>, defaults?: AddressDemographicDefaults): Partial<AddressDemographic> => ({
    asOf: asOf(),
    type: undefined,
    use: undefined,
    address1: undefined,
    address2: undefined,
    city: undefined,
    state: defaults?.state,
    zipcode: undefined,
    county: undefined,
    country: defaults?.country,
    censusTract: undefined,
    comment: undefined
});

export type { AddressDemographicDefaults };
export { initial };

const labels = {
    asOf: 'As of',
    type: 'Type',
    use: 'Use',
    address1: 'Street address 1',
    address2: 'Street address 2',
    city: 'City',
    state: 'State',
    zip: 'Zip',
    county: 'County',
    censusTract: 'Census tract',
    country: 'Country',
    comment: 'Comments'
};

export { labels };
