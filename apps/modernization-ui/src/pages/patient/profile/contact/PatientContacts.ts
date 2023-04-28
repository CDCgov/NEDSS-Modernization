export type Contact = {
    id: string;
    name: string;
};

export type AssociatedWith = {
    id: string;
    condition: string;
    local: string;
};

export type Tracing = {
    contactRecord: string;
    createdOn: any;
    condition?: string | null;
    namedOn: any;
    priority?: string | null;
    disposition?: string | null;
    event: string;
    contact: Contact;
    associatedWith?: AssociatedWith | null;
};
