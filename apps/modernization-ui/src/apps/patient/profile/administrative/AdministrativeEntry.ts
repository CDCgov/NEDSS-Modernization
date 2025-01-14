export type AdministrativeEntry = {
    asOf: string | null | undefined;
    comment: string | null | undefined;
};

export enum Column {
    AsOf = 'As of',
    Comment = 'Additional comments',
    Actions = 'Actions'
}
