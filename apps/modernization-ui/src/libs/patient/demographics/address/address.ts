import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type AddressDemographic = EffectiveDated & {
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
