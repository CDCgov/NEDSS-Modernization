import { Supplier } from 'libs/supplying';
import { Selectable } from 'options';
import { EffectiveDated, orNull } from 'utils';
import { Nullable } from 'utils/object';

type AddressDemographic = EffectiveDated &
    Nullable<{
        identifier?: number;
        type: Selectable;
        use: Selectable;
        address1: string;
        address2: string;
        city: string;
        state: Selectable;
        zipcode: string;
        county: Selectable;
        censusTract: string;
        country: Selectable;
        comment?: string;
    }>;

type HasAddressDemographics = {
    addresses?: AddressDemographic[];
};

export type { AddressDemographic, HasAddressDemographics };

type AddressDemographicDefaults = {
    state?: Selectable;
    country?: Selectable;
};

const initial = (asOf: Supplier<string>, defaults?: AddressDemographicDefaults): AddressDemographic => ({
    asOf: asOf(),
    type: null,
    use: null,
    address1: null,
    address2: null,
    city: null,
    state: orNull(defaults?.state),
    zipcode: null,
    county: null,
    country: orNull(defaults?.country),
    censusTract: null,
    comment: null
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
