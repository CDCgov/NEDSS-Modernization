import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';

type PhoneEmailDemographic = EffectiveDated & {
    type: Selectable;
    use: Selectable;
    countryCode?: string;
    phoneNumber?: string;
    extension?: string;
    email?: string;
    url?: string;
    comment?: string;
};

type HasPhoneEmailDemographics = {
    phoneEmails?: PhoneEmailDemographic[];
};

export type { PhoneEmailDemographic, HasPhoneEmailDemographics };

const initial = (asOf: string = today()): Partial<PhoneEmailDemographic> => ({
    asOf,
    type: undefined,
    use: undefined,
    countryCode: undefined,
    phoneNumber: undefined,
    extension: undefined,
    email: undefined,
    url: undefined,
    comment: undefined
});

export { initial };
