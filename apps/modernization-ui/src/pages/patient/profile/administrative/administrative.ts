export type AdministrativeEntry = {
    patient: number;
    asOf: string | null;
    comment: string | null;
};

export enum Column {
    AsOf = 'As of',
    Comment = 'Comment',
    Actions = 'Actions'
}

export type Administrative = {
    asOf: Date;
    comment: string;
    patient: string | number;
    version: number | null;
};
