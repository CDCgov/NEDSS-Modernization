import { Selectable } from 'options';
import { EffectiveDated, HasComments, Maybe } from 'utils';

type LocationEntry = {
    city?: string;
    state?: Selectable;
    county?: Selectable;
    country?: Selectable;
};

type AdministrativeEntry = EffectiveDated & HasComments;

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
    id: string | null;
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
    PhoneEmailEntry,
    IdentificationEntry,
    SexEntry,
    BirthEntry,
    MortalityEntry,
    GeneralInformationEntry
};
