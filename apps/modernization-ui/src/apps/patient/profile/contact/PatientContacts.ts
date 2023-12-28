export type Contact = {
    id: string;
    name: string;
};

export const isContact = (obj: any): obj is Contact => 'id' in obj && 'name' in obj;

export type AssociatedWith = {
    id: string;
    condition: string;
    local: string;
};

export const isAssociatedWith = (obj: any): obj is AssociatedWith =>
    'id' in obj && 'condition' in obj && 'local' in obj;

export type Condition = {
    id: string | null;
    description: string | null;
};

export const isCondition = (obj: any): obj is Contact => 'id' in obj && 'description' in obj;

export type Tracing = {
    contactRecord: string;
    createdOn: Date;
    condition?: Condition | null;
    namedOn: Date;
    priority?: string | null;
    disposition?: string | null;
    event: string;
    contact: Contact;
    associatedWith?: AssociatedWith | null;
};

export enum Headers {
    DateCreated = 'Date created',
    NamedBy = 'Named by',
    ContactsNamed = 'Contacts named',
    DateNamed = 'Date named',
    Description = 'Description',
    AssociatedWith = 'Associated with',
    Event = 'Event #'
}
