import { EffectiveDated, HasComments } from 'utils';

type AddressDemographicRequest = EffectiveDated &
    HasComments & {
        identifier?: number;
        type: string;
        use: string;
        address1?: string;
        address2?: string;
        city?: string;
        county?: string;
        state?: string;
        zipcode?: string;
        country?: string;
        censusTract?: string;
    };

export type { AddressDemographicRequest };
