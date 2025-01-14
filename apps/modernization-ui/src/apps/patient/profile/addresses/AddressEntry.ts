type AddressFields = {
    asOf: string | null;
    type: string | null;
    use: string | null;
    address1: string | null;
    address2: string | null;
    city: string | null;
    state: string | null;
    zipcode: string | null;
    county: string | null;
    country: string | null;
    censusTract: string | null;
    comment: string | null;
};

type NewAddressEntry = { patient: number } & AddressFields;

type UpdateAddressEntry = { patient: number; id: number } & AddressFields;

type AddressEntry = NewAddressEntry | UpdateAddressEntry;

const isAdd = (obj: AddressEntry): obj is NewAddressEntry => !('id' in obj);
const isUpdate = (obj: AddressEntry): obj is NewAddressEntry => 'id' in obj;

export type { AddressEntry, NewAddressEntry, UpdateAddressEntry, AddressFields };

export { isAdd, isUpdate };
