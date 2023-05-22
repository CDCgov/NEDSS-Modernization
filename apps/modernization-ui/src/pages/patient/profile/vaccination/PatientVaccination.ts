type AssociatedWith = {
    id: string;
    condition: string;
    local: string;
};

const isAssociatedWith = (obj: any): obj is AssociatedWith => 'id' in obj && 'condition' in obj && 'local' in obj;

type Vaccination = {
    vaccination: string;
    createdOn: Date;
    provider?: string | null;
    administeredOn?: Date | null;
    administered: string;
    event: string;
    associatedWith?: AssociatedWith | null;
};

enum Headers {
    DateCreated = 'Date created',
    Provider = 'Provider',
    DateAdministered = 'Date administered',
    VaccineAdministered = 'Vaccine administered',
    AssociatedWith = 'Associated with',
    Event = 'Event #'
}

export { isAssociatedWith, Headers };
export type { AssociatedWith, Vaccination };
