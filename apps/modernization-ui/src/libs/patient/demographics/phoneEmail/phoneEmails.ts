import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';
import { Selectable } from 'options';
import { Supplier } from 'libs/supplying';

type PhoneEmailDemographic = EffectiveDated & { identifier?: number } & Nullable<{
        type?: Selectable;
        use?: Selectable;
        countryCode?: string;
        phoneNumber?: string;
        extension?: string;
        email?: string;
        url?: string;
        comment?: string;
    }>;

type HasPhoneEmailDemographics = {
    phoneEmails?: PhoneEmailDemographic[];
};

export type { PhoneEmailDemographic, HasPhoneEmailDemographics };

const initial = (asOf: Supplier<string>): PhoneEmailDemographic => ({
    asOf: asOf(),
    type: null,
    use: null,
    countryCode: null,
    phoneNumber: null,
    extension: null,
    email: null,
    url: null,
    comment: null
});

export { initial };

const labels = {
    asOf: 'As of',
    type: 'Type',
    use: 'Use',
    countryCode: 'Country code',
    phoneNumber: 'Phone number',
    extension: 'Extension',
    email: 'Email',
    url: 'URL',
    comment: 'Comments'
};

export { labels };
