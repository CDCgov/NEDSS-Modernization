type PhoneEmailFields = {
    asOf: string | null;
    type: string | null;
    use: string | null;
    countryCode: string | null;
    number: string | null;
    extension: string | null;
    email: string | null;
    url: string | null;
    comment: string | null;
};

type NewPhoneEmailEntry = { patient: number } & PhoneEmailFields;

type UpdatePhoneEmailEntry = { patient: number; id: number } & PhoneEmailFields;

type PhoneEmailEntry = NewPhoneEmailEntry | UpdatePhoneEmailEntry;

const isAdd = (obj: PhoneEmailEntry): obj is NewPhoneEmailEntry => !('id' in obj);
const isUpdate = (obj: PhoneEmailEntry): obj is NewPhoneEmailEntry => 'id' in obj;

export type { PhoneEmailEntry, NewPhoneEmailEntry, UpdatePhoneEmailEntry, PhoneEmailFields };

export { isAdd, isUpdate };
