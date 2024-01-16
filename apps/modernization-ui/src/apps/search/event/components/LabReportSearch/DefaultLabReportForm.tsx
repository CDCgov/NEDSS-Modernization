import { EntryMethod, EventStatus, LabReportFilter, LaboratoryReportStatus, UserType } from 'generated/graphql/schema';

const initialLabForm = (): LabReportFilter => {
    return {
        codedResult: undefined,
        createdBy: undefined,
        enteredBy: [UserType.External, UserType.Internal],
        entryMethods: [EntryMethod.Electronic],
        eventDate: undefined,
        eventId: undefined,
        eventStatus: [EventStatus.New],
        jurisdictions: [],
        lastUpdatedBy: undefined,
        patientId: undefined,
        pregnancyStatus: undefined,
        processingStatus: [LaboratoryReportStatus.Unprocessed],
        programAreas: [],
        providerSearch: undefined,
        resultedTest: undefined
    };
};

export { initialLabForm };
