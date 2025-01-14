import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated, HasComments } from 'utils';

type AddressEntry = EffectiveDated &
    HasComments & {
        type: Selectable | null;
        use: Selectable | null;
        address1?: string;
        address2?: string;
        city?: string;
        county?: Selectable | null;
        state?: Selectable | null;
        zipcode?: string;
        country?: Selectable | null;
        censusTract?: string;
    };

export type { AddressEntry };

const initial = (asOf: string = today()): Partial<AddressEntry> => ({
    asOf,
    type: undefined,
    use: undefined,
    address1: '',
    address2: '',
    city: '',
    state: undefined,
    zipcode: '',
    county: undefined,
    country: {
        value: '840',
        name: 'United States'
    },
    censusTract: '',
    comment: ''
});

export { initial };
