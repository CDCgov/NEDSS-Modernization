export interface AssociatedWith {
    id: string;
    local: string;
}

export const isAssociatedWith = (obj: any): obj is AssociatedWith => 'id' in obj && 'local' in obj;

export type Document = {
    document: string;
    receivedOn: Date;
    type: string;
    sendingFacility: string;
    reportedOn: Date;
    condition?: string | null;
    event: string;
    associatedWith?: AssociatedWith | null;
};

export enum Headers {
    DateReceived = 'Date received',
    Type = 'Type',
    SendingFacility = 'Sending facility',
    DateReported = 'Date reported',
    Condition = 'Condition',
    AssociatedWith = 'Associated with',
    EventID = 'Event ID'
}
