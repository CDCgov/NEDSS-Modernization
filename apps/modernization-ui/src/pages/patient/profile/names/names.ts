export type Name = {
    __typename?: 'PatientName';
    patient: string;
    version: number;
    asOf: any;
    sequence: number;
    first?: string | null;
    middle?: string | null;
    secondMiddle?: string | null;
    last?: string | null;
    secondLast?: string | null;
    use: { __typename?: 'PatientCodedValue'; id: string; description: string };
    prefix?: { __typename?: 'PatientCodedValue'; id: string; description: string } | null;
    suffix?: { __typename?: 'PatientCodedValue'; id: string; description: string } | null;
    degree?: { __typename?: 'PatientCodedValue'; id: string; description: string } | null;
} | null;

export type Names = {
    content: Name[];
    number?: number;
    size?: number;
    total?: number;
    __typename?: 'PatientNameResults';
};
