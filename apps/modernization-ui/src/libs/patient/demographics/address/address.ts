import { today } from 'date';
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

const initial = (asOf: string = today()): Partial<AddressDemographic> => ({
    asOf,
    type: undefined,
    use: undefined,
    address1: undefined,
    address2: undefined,
    city: undefined,
    state: undefined,
    zipcode: undefined,
    county: undefined,
    country: undefined,
    censusTract: undefined,
    comment: undefined
});

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
