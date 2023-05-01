export type FindPatientProfileQuery = {
    __typename?: 'Query';
    findPatientProfile?: {
        __typename?: 'PatientProfile';
        id: string;
        local: string;
        shortId?: number | null;
        version: number;
        administrative?: {
            __typename?: 'PatientAdministrativeResults';
            total: number;
            number: number;
            size: number;
            content: Array<{
                __typename?: 'PatientAdministrative';
                patient: string;
                id: string;
                version: number;
                asOf: any;
                comment?: string | null;
            } | null>;
        } | null;
    } | null;
};

export type Administrative = {
    __typename?: 'PatientAdministrative';
    patient: string;
    id: string;
    version: number;
    asOf: any;
    comment?: string | null;
} | null;
