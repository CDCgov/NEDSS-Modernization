export type AdministrativeEntry = {
    asOf: string | null;
    comment: string | null;
};

export enum Column {
    AsOf = 'As of',
    Comment = 'Additional comments',
    Actions = 'Actions'
}
