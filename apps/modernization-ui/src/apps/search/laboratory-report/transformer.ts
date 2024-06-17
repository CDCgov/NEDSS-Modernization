import {
    EntryMethod,
    EventStatus,
    LaboratoryReportStatus,
    LabReportFilter,
    PregnancyStatus,
    UserType
} from 'generated/graphql/schema';
import { LabReportFilterEntry } from './labReportFormTypes';
import { asValue, asValues } from 'options/selectable';

export const transformObject = (data: LabReportFilterEntry): LabReportFilter => {
    return {
        ...data,
        codedResult: data.codedResult ? (asValue(data.codedResult) as string) : undefined,
        createdBy: data.createdBy ? (asValue(data.createdBy) as string) : undefined,
        jurisdictions: data.jurisdictions ? (asValues(data.jurisdictions) as string[]) : undefined,
        eventStatus: data.eventStatus ? (asValues(data.eventStatus) as EventStatus[]) : undefined,
        processingStatus: data.processingStatus
            ? (asValues(data.processingStatus) as LaboratoryReportStatus[])
            : undefined,
        programAreas: data.programAreas ? (asValues(data.programAreas) as string[]) : undefined,
        resultedTest: data.resultedTest ? (asValue(data.resultedTest) as string) : undefined,
        entryMethods: data.entryMethods ? (asValues(data.entryMethods) as EntryMethod[]) : undefined,
        enteredBy: data.enteredBy ? (asValues(data.enteredBy) as UserType[]) : undefined,
        lastUpdatedBy: data.lastUpdatedBy ? (asValue(data.lastUpdatedBy) as string) : undefined,
        pregnancyStatus: data.pregnancyStatus ? (asValue(data.pregnancyStatus) as PregnancyStatus) : undefined
    };
};
