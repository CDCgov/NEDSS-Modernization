export type IdentificationEntry = {
    patient: number;
    asOf: string | null;
    type: string | null;
    value: string | null;
    state: string | null;
    sequence?: number;
};

export enum Headers {
    AsOf = 'As of',
    Type = 'Type',
    Authority = 'Authority',
    Value = 'Value',
    Actions = 'Actions'
}
