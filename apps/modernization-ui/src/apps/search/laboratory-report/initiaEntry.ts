import { LabReportFilterEntry } from 'apps/search/laboratory-report/labReportFormTypes';
import { EntryMethod, EventStatus, LaboratoryReportStatus, UserType } from 'generated/graphql/schema';

const initialEntry = (): LabReportFilterEntry => {
    return {
        codedResult: undefined,
        createdBy: undefined,
        enteredBy: [
            { name: UserType.External, value: UserType.External, label: UserType.External },
            { name: UserType.Internal, value: UserType.Internal, label: UserType.Internal }
        ],
        entryMethods: [{ name: EntryMethod.Electronic, value: EntryMethod.Electronic, label: EntryMethod.Electronic }],
        eventDate: undefined,
        eventId: undefined,
        eventStatus: [{ name: EventStatus.New, value: EventStatus.New, label: EventStatus.New }],
        jurisdictions: [],
        lastUpdatedBy: undefined,
        patientId: undefined,
        pregnancyStatus: undefined,
        processingStatus: [
            {
                name: LaboratoryReportStatus.Unprocessed,
                value: LaboratoryReportStatus.Unprocessed,
                label: LaboratoryReportStatus.Unprocessed
            }
        ],
        programAreas: [],
        providerSearch: undefined,
        resultedTest: undefined
    };
};

export { initialEntry };
