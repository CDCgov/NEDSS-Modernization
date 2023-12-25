export type IdentificationEntry = {
    patient: number;
    asOf: string | null;
    type: string | null;
    value: string | null;
    state: string | null;
    sequence?: number;
};
