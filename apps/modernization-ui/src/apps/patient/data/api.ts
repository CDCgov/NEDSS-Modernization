import { EffectiveDated, HasComments } from 'utils';

type HasLocation = {
    city?: string;
    state?: string;
    county?: string;
    country?: string;
};

type Administrative = EffectiveDated & HasComments;

type Name = EffectiveDated & {
    type: string;
    prefix?: string;
    first?: string;
    middle?: string;
    secondMiddle?: string;
    last?: string;
    secondLast?: string;
    suffix?: string;
    degree?: string;
};

type Address = EffectiveDated &
    HasComments & {
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

type PhoneEmail = EffectiveDated &
    HasComments & {
        type: string;
        use: string;
        countryCode?: string;
        phoneNumber?: string;
        extension?: string;
        email?: string;
        url?: string;
    };

type Identification = EffectiveDated & {
    type: string;
    id: string;
    issuer?: string;
};

type Ethnicity = EffectiveDated & {
    ethnicGroup: string;
    detailed: string[];
    unknownReason?: string;
};

type Sex = EffectiveDated & {
    current?: string;
    unknownReason?: string;
    transgenderInformation?: string;
    additionalGender?: string;
};

type Birth = EffectiveDated &
    HasLocation & {
        bornOn?: string;
        sex?: string;
        multiple?: string;
        order?: number;
    };

type Mortality = EffectiveDated &
    HasLocation & {
        deceased?: string;
        deceasedOn?: string;
    };

type GeneralInformation = EffectiveDated &
    HasComments & {
        maritalStatus?: string;
        maternalMaidenName?: string;
        adultsInResidence?: number;
        childrenInResidence?: number;
        primaryOccupation?: string;
        educationLevel?: string;
        primaryLanguage?: string;
        speaksEnglish?: string;
        stateHIVCase?: string;
    };

export type {
    Administrative,
    Name,
    Address,
    PhoneEmail,
    Identification,
    Ethnicity,
    Sex,
    Birth,
    Mortality,
    GeneralInformation
};
