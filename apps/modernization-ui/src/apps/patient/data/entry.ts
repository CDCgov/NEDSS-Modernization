import { Selectable } from 'options';
import { EffectiveDated, HasComments, Maybe } from 'utils';

type LocationEntry = {
    city: Maybe<string>;
    state: Maybe<Selectable>;
    county: Maybe<Selectable>;
    country: Maybe<Selectable>;
};

type AdministrativeEntry = EffectiveDated & HasComments;

type NameEntry = EffectiveDated & {
    type: Selectable;
    prefix?: Maybe<Selectable>;
    first?: Maybe<string>;
    middle?: Maybe<string>;
    secondMiddle?: Maybe<string>;
    last?: Maybe<string>;
    secondLast?: Maybe<string>;
    suffix?: Maybe<Selectable>;
    degree?: Maybe<Selectable>;
};

type AddressEntry = EffectiveDated &
    HasComments & {
        type: Selectable;
        use: Selectable;
        address1: Maybe<string>;
        address2: Maybe<string>;
        city: Maybe<string>;
        state: Maybe<Selectable>;
        zipcode: Maybe<string>;
        county: Maybe<Selectable>;
        country: Maybe<Selectable>;
        censusTract: Maybe<string>;
    };

type PhoneEmailEntry = EffectiveDated &
    HasComments & {
        type: Selectable;
        use: Selectable;
        countryCode: Maybe<string>;
        phoneNumber: Maybe<string>;
        extension: Maybe<string>;
        email: Maybe<string>;
        url: Maybe<string>;
    };

type IdentificationEntry = EffectiveDated & {
    type: Selectable;
    id: string;
    issuer: Maybe<Selectable>;
};

type RaceEntry = EffectiveDated & {
    race: Selectable;
    detailed: Selectable[];
};

type EthnicityEntry = EffectiveDated & {
    ethnicity: Maybe<Selectable>;
    detailed: Selectable[];
};

type SexEntry = EffectiveDated & {
    currentSex: Maybe<Selectable>;
    unknownReason: Maybe<Selectable>;
    transgenderInformation: Maybe<Selectable>;
    additionalGender: Maybe<string>;
};

type BirthEntry = EffectiveDated &
    LocationEntry & {
        dateOfBirth: Maybe<String>;
        sex: Maybe<Selectable>;
        multiple: Maybe<Selectable>;
        order: Maybe<number>;
    };

type MortalityEntry = EffectiveDated &
    LocationEntry & {
        deceased: Maybe<Selectable>;
        deceasedOn: Maybe<string>;
    };

type GeneralInformationEntry = EffectiveDated &
    HasComments & {
        maritalStatus: Maybe<Selectable>;
        maternalMaidenName: Maybe<string>;
        adultsInResidence?: Maybe<number>;
        childrenInResidence?: Maybe<number>;
        primaryOccupation: Maybe<Selectable>;
        educationLevel: Maybe<Selectable>;
        speaksEnglish: Maybe<Selectable>;
        stateHIVCase?: Maybe<string>;
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
