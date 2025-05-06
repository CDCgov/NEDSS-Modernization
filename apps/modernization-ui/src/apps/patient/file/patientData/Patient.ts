export type Patient = {
    id: string;
    local: string;
    version: number;
    shortId: number;
    /** Whether the patient is deletable: has no associations and is active */
    deletable: boolean;
    status: string;
};
