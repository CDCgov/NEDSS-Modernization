export type AssociatedWith = {
    id: string;
    local: string;
};

export type Document = {
    document: string;
    receivedOn: any;
    type: string;
    sendingFacility: string;
    reportedOn: any;
    condition?: string | null | undefined;
    event: string;
    associatedWith?: AssociatedWith | null;
};
