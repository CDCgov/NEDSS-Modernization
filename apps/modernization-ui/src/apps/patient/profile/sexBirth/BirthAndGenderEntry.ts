type BirthEntry = {
    bornOn: string | null;
    gender: string | null;
    multipleBirth: string | null;
    birthOrder: number | null;
    city: string | null;
    state: string | null;
    county: string | null;
    country: string | null;
};

type GenderEntry = {
    current: string | null;
    unknownReason: string | null;
    preferred: string | null;
    additional: string | null;
};

export type BirthAndGenderEntry = {
    asOf: string | null;
    birth: BirthEntry;
    gender: GenderEntry;
};
