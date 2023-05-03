import { Email } from './PatientSummary';
import { Address, Name, PatientSummary, Phone } from './PatientSummary';

type PatientLegalName = {
    __typename?: 'PatientLegalName';
    prefix?: string | null;
    first?: string | null;
    middle?: string | null;
    last?: string | null;
    suffix?: string | null;
};

type PatientSummaryAddress = {
    __typename?: 'PatientSummaryAddress';
    street?: string | null;
    city?: string | null;
    state?: string | null;
    zipcode?: string | null;
    country?: string | null;
};

type PatientSummaryPhone = { __typename?: 'PatientSummaryPhone'; use?: string | null; number?: string | null };

type PatientSummaryEmail = { __typename?: 'PatientSummaryEmail'; use?: string | null; address?: string | null };

type Result =
    | {
          __typename?: 'PatientSummary';
          birthday?: any | null;
          age?: number | null;
          gender?: string | null;
          ethnicity?: string | null;
          race?: string | null;
          legalName?: PatientLegalName | null;
          phone?: Array<PatientSummaryPhone | null> | null;
          email?: Array<PatientSummaryEmail | null> | null;
          address?: PatientSummaryAddress | null;
      }
    | null
    | undefined;

const mapNonNull = <R, S>(fn: (r: R) => S | null, items: (R | null)[] | null | undefined): S[] => {
    if (items) {
        return items.reduce((existing: S[], next: R | null) => {
            if (next) {
                const mapped = fn(next);
                if (mapped) {
                    return [...existing, mapped];
                }
            }
            return existing;
        }, []);
    } else {
        return [];
    }
};

const orNull = (value: string | undefined | null): string | null => (value && value) || null;

const ensureName = (name: PatientLegalName): Name => ({
    prefix: orNull(name.prefix),
    first: orNull(name.first),
    middle: orNull(name.middle),
    last: orNull(name.last),
    suffix: orNull(name.suffix)
});

const ensureAddress = (address: PatientSummaryAddress): Address => ({
    street: orNull(address.street),
    city: orNull(address.city),
    state: orNull(address.state),
    zipcode: orNull(address.zipcode),
    country: orNull(address.country)
});

const ensurePhone = ({ use, number }: PatientSummaryPhone): Phone | null => {
    return use && number ? { use, number } : null;
};

const ensureEmail = ({ use, address }: PatientSummaryEmail): Email | null => {
    return use && address ? { use, address } : null;
};

export const transform = (result: Result): PatientSummary | undefined => {
    if (result) {
        return {
            birthday: orNull(result.birthday),
            age: result.age ?? null,
            gender: orNull(result.gender),
            ethnicity: orNull(result.ethnicity),
            race: orNull(result.race),
            legalName: result.legalName ? ensureName(result.legalName) : null,
            address: result.address ? ensureAddress(result.address) : null,
            phone: mapNonNull(ensurePhone, result.phone),
            email: mapNonNull(ensureEmail, result.email)
        };
    }
};
