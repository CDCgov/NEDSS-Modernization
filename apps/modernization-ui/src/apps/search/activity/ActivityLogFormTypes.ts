import { Selectable } from 'options';
import { asSelectable } from 'options/selectable';

type EventDate = {
    from: string;
    fromTime: string;
    to: string;
    toTime: string;
};

type ActivityFilterEntry = BasicFilter & ElrCriteriaFilter;

type BasicFilter = {
    reportType?: Selectable;
    eventDate?: EventDate;
    status?: Selectable[];
};

type ElrCriteriaFilter = {
    processedTime?: string;
    algorithmName?: string;
    action?: string;
    messageId?: string;
    sourceNm?: Selectable;
    entityNm?: string;
    observationId?: string;
    accessionNumber?: string;
    exceptionText?: Selectable;
};

export type { ActivityFilterEntry, EventDate, ElrCriteriaFilter };

const reportType = [asSelectable('ELR', 'Electronic Lab Report'), asSelectable('ECR', 'Electronic Case Report')];
const statusOptions: Selectable[] = [asSelectable('Success', 'Success'), asSelectable('Failure', 'Failure')];

export { reportType, statusOptions };
