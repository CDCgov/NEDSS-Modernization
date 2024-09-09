import { EffectiveDated, HasComments, Maybe } from 'utils';

type HasLocation = {
    city: Maybe<string>;
    state: Maybe<string>;
    county: Maybe<string>;
    country: Maybe<string>;
};

type Administrative = EffectiveDated & HasComments;

type Name = EffectiveDated & {
    type: string;
    prefix: Maybe<string>;
    first: Maybe<string>;
    middle: Maybe<string>;
    secondMiddle: Maybe<string>;
    last: Maybe<string>;
    secondLast: Maybe<string>;
    suffix: Maybe<string>;
    degree: Maybe<string>;
};

type Address = EffectiveDated &
    HasComments & {
        type: string;
        use: string;
        address1: Maybe<string>;
        address2: Maybe<string>;
        city: Maybe<string>;
        state: Maybe<string>;
        zipcode: Maybe<string>;
        county: Maybe<string>;
        country: Maybe<string>;
        censusTract: Maybe<string>;
    };

type PhoneEmail = EffectiveDated &
    HasComments & {
        type: string;
        use: string;
        countryCode: Maybe<string>;
        phoneNumber: Maybe<string>;
        extension: Maybe<string>;
        email: Maybe<string>;
        url: Maybe<string>;
    };

type Identification = EffectiveDated & {
    type: string;
    id: string;
    issuer: Maybe<string>;
};

type Race = EffectiveDated & {
    race: string;
    detailed: string[];
};

type Ethnicity = EffectiveDated & {
    ethnicity: Maybe<string>;
    detailed: string[];
};

type Sex = {
    currentSex: Maybe<string>;
    unknownReason: Maybe<string>;
    transgenderInformation: Maybe<string>;
    additionalGender: Maybe<string>;
};

type Birth = EffectiveDated &
    HasLocation & {
        dateOfBirth: Maybe<string>;
        sex: Maybe<string>;
        multiple: Maybe<string>;
        order: Maybe<number>;
    };

type Mortality = EffectiveDated &
    HasLocation & {
        deceased: Maybe<string>;
        deceasedOn: Maybe<string>;
    };

type GeneralInformation = EffectiveDated &
    HasComments & {
        maritalStatus: Maybe<string>;
        maternalMaidenName: Maybe<string>;
        adultsInResidence: Maybe<number>;
        childrenInResidence: Maybe<number>;
        primaryOccupation: Maybe<string>;
        educationLevel: Maybe<string>;
        speaksEnglish: Maybe<string>;
        stateHIVCase: Maybe<string>;
    };

export type {
    Administrative,
    Name,
    Address,
    PhoneEmail,
    Identification,
    Race,
    Ethnicity,
    Sex,
    Birth,
    Mortality,
    GeneralInformation
};
