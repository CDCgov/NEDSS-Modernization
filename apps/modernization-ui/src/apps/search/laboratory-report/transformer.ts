import {
    EntryMethod,
    EventStatus,
    LaboratoryEventDateSearch,
    LaboratoryEventIdType,
    LaboratoryReportEventDateType,
    LaboratoryReportStatus,
    LabReportEventId,
    LabReportFilter,
    LabReportProviderSearch,
    PregnancyStatus,
    ProviderType,
    UserType
} from 'generated/graphql/schema';
import { EventDate, Identification, LabReportFilterEntry } from './labReportFormTypes';
import { asValue, asValues, Selectable } from 'options/selectable';

const transformObject = (data: LabReportFilterEntry): LabReportFilter => {
    const { orderingFacility, orderingProvider, reportingFacility, ...remaining } = data;

    const providerSearch =
        resolveOrderingFacility(orderingFacility) ||
        resolveOrderingProvider(orderingProvider) ||
        resolveReportingFacility(reportingFacility);

    return {
        codedResult: asValue(remaining.codedResult),
        createdBy: asValue(data.createdBy),
        jurisdictions: remaining.jurisdictions && asValues(remaining.jurisdictions),
        eventStatus: remaining.eventStatus && (asValues(remaining.eventStatus) as EventStatus[]),
        processingStatus:
            remaining.processingStatus && (asValues(remaining.processingStatus) as LaboratoryReportStatus[]),
        programAreas: remaining.programAreas && asValues(remaining.programAreas),
        resultedTest: asValue(remaining.resultedTest),
        entryMethods: remaining.entryMethods && (asValues(remaining.entryMethods) as EntryMethod[]),
        enteredBy: remaining.enteredBy && (asValues(remaining.enteredBy) as UserType[]),
        lastUpdatedBy: asValue(remaining.updatedBy),
        pregnancyStatus: remaining.pregnancyStatus && (asValue(remaining.pregnancyStatus) as PregnancyStatus),
        eventId: resolveEventId(remaining.identification),
        eventDate: resolveEventDate(remaining.eventDate),
        providerSearch
    };
};

const resolveEventDate = (date?: EventDate): LaboratoryEventDateSearch | undefined => {
    if (date && date.type?.value) {
        return {
            type: date.type.value as LaboratoryReportEventDateType,
            from: date.from,
            to: date.to
        };
    }
};

const resolveEventId = (identification?: Identification): LabReportEventId | undefined => {
    if (identification) {
        return {
            labEventId: identification.value,
            labEventType: identification.type.value as LaboratoryEventIdType
        };
    }
};

const resolveProvider =
    (type: ProviderType) =>
    (selectable?: Selectable): LabReportProviderSearch | undefined =>
        selectable && {
            providerId: selectable.value,
            providerType: type
        };

const resolveOrderingFacility = resolveProvider(ProviderType.OrderingFacility);
const resolveOrderingProvider = resolveProvider(ProviderType.OrderingProvider);
const resolveReportingFacility = resolveProvider(ProviderType.ReportingFacility);

export { transformObject };
