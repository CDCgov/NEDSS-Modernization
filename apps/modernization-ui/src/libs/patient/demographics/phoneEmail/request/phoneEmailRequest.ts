import { EffectiveDated, HasComments } from 'utils';

type PhoneEmailDemographicRequest = EffectiveDated &
    HasComments & {
        type: string;
        use: string;
        countryCode?: string;
        phoneNumber?: string;
        extension?: string;
        email?: string;
        url?: string;
    };

export type { PhoneEmailDemographicRequest };
