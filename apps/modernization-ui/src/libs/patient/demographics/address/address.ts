import { EffectiveDated } from 'utils';

type address = EffectiveDated & {
    type?: string;
    use?: string;
    address1?: string;
    address2?: string;
    city?: string;
    state?: string;
    zipcode?: string;
    county?: string;
    censusTract?: string;
    country?: string;
    comment?: string;
};
