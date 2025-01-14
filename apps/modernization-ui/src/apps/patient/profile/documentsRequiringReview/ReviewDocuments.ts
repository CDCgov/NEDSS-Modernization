import { Maybe } from 'graphql/jsutils/Maybe';

export type DocumentReview = {
    dateReceived: Date;
    descriptions: Array<Maybe<Description>>;
    eventDate?: Date | null;
    id: string;
    facilityProviders: FacilityProviders;
    isElectronic: boolean;
    localId: string;
    type: string;
};

type FacilityProviders = {
    orderingProvider?: Maybe<OrderingProvider>;
    reportingFacility?: Maybe<ReportingFacility>;
    sendingFacility?: Maybe<SendingFacility>;
};

type SendingFacility = {
    name?: string | null;
};

type ReportingFacility = {
    name?: string | null;
};

type OrderingProvider = {
    name?: string | null;
};

type Description = {
    title?: string | null;
    value?: string | null;
};

export enum Columns {
    DocumentType = 'Document type',
    DateReceived = 'Date received',
    ReportingFacilityProvider = 'Reporting facility / provider',
    EventDate = 'Event date',
    Description = 'Description',
    EventID = 'Event #'
}
