import { Selectable } from 'options';
import { EffectiveDated, HasComments } from 'utils';

type LocationEntry = {
    city?: string;
    state?: Selectable;
    county?: Selectable;
    country?: Selectable;
};

type AdministrativeEntry = EffectiveDated & HasComments;

type NameEntry = EffectiveDated & {
    type: Selectable;
    prefix?: Selectable;
    first?: string;
    middle?: string;
    secondMiddle?: string;
    last?: string;
    secondLast?: string;
    suffix?: Selectable;
    degree?: Selectable;
};

type AddressEntry = EffectiveDated &
    HasComments & {
        type: Selectable;
        use: Selectable;
        address1?: string;
        address2?: string;
        city?: string;
        county?: Selectable;
        state?: Selectable;
        zipcode?: string;
        country?: Selectable;
        censusTract?: string;
    };

type PhoneEmailEntry = EffectiveDated &
    HasComments & {
        type: Selectable;
        use: Selectable;
        countryCode?: string;
        phoneNumber?: string;
        extension?: string;
        email?: string;
        url?: string;
    };

type IdentificationEntry = EffectiveDated & {
    type: Selectable;
    id: string;
    issuer?: Selectable;
};

type RaceEntry = EffectiveDated & {
    race: Selectable;
    detailed: Selectable[];
};

type EthnicityEntry = EffectiveDated & {
    ethnicity: Selectable;
    detailed: Selectable[];
    reasonUnknown?: Selectable;
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
    RaceEntry,
    EthnicityEntry,
    SexEntry,
    BirthEntry,
    MortalityEntry,
    GeneralInformationEntry
};
