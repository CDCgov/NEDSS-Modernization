export type Pages = {
    pageName: string;
    eventType: string;
    relatedConditions: string;
    status: string;
    lastUpdatedBy: any;
    lastUpdatedDate: any;
    template: string;
};

export enum Headers {
    PageName = 'Page name',
    EventType = 'Event name',
    RelatedConditions = 'Related conditions',
    Status = 'Status',
    LastUpdatedBy = 'Last updated by'
}
