import { Selectable } from 'options';
import { EffectiveDated, HasComments, Maybe } from 'utils';

type LocationEntry = {
    city?: string;
    state?: Selectable;
    county?: Selectable;
    country?: Selectable;
};

type AdministrativeEntry = EffectiveDated & HasComments;

type NameEntry = EffectiveDated & {
    type: Selectable | null;
    prefix?: Maybe<Selectable>;
    first?: string;
    middle?: string;
    secondMiddle?: string;
    last?: string;
    secondLast?: string;
    suffix?: Maybe<Selectable>;
    degree?: Maybe<Selectable>;
};

type AddressEntry = EffectiveDated &
    HasComments & {
        type: Selectable | null;
        use: Selectable | null;
        address1?: string;
        address2?: string;
        city?: string;
        county?: Maybe<Selectable>;
        state?: Maybe<Selectable>;
        zipcode?: string;
        country?: Maybe<Selectable>;
        censusTract?: string;
    };

type PhoneEmailEntry = EffectiveDated &
    HasComments & {
        type: Selectable | null;
        use: Selectable | null;
        countryCode?: string;
        phoneNumber?: string;
        extension?: string;
        email?: string;
        url?: string;
    };

type IdentificationEntry = EffectiveDated & {
    type: Selectable | null;
    id: string;
    issuer?: Maybe<Selectable>;
};

type SexEntry = EffectiveDated & {
    current?: Selectable;
    unknownReason?: Selectable;
    transgenderInformation?: Selectable;
    additionalGender?: string;
};

type BirthEntry = EffectiveDated &
    LocationEntry & {
        bornOn?: string;
        sex?: Selectable;
        multiple?: Selectable;
        order?: number;
    };

type MortalityEntry = EffectiveDated &
    LocationEntry & {
        deceased?: Selectable;
        deceasedOn?: string;
    };

type GeneralInformationEntry = EffectiveDated & {
    maritalStatus?: Selectable;
    maternalMaidenName?: string;
    adultsInResidence?: number;
    childrenInResidence?: number;
    primaryOccupation?: Selectable;
    educationLevel?: Selectable;
    primaryLanguage?: Selectable;
    speaksEnglish?: Selectable;
    stateHIVCase?: string;
};

export type {
    AdministrativeEntry,
    NameEntry,
    AddressEntry,
    PhoneEmailEntry,
    IdentificationEntry,
    SexEntry,
    BirthEntry,
    MortalityEntry,
    GeneralInformationEntry
};
