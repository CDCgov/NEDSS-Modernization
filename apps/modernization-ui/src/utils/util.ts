import { InvestigationFilter, LabReportFilter } from '../generated/graphql/schema';

export const TOTAL_TABLE_DATA = 10;

export const formatInterfaceString = (str: string) => {
    let i;
    const frags = str.split('_');
    for (i = 0; i < frags.length; i++) {
        frags[i] = frags[i].charAt(0).toUpperCase() + frags[i].slice(1).toLowerCase();
    }
    return frags.join(' ');
};

export const convertCamelCase = (str: string) => {
    const result = str.replace(/([A-Z])/g, ' $1');
    const finalResult = result.charAt(0).toUpperCase() + result.slice(1);
    return finalResult || str;
};

export const calculateAge = (birthday: Date) => {
    // birthday is a date
    const ageDifMs = Date.now() - birthday.getTime();
    const ageDate = new Date(ageDifMs); // miliseconds from epoch
    if (Math.abs(ageDate.getMonth()) === 0 && Math.abs(ageDate.getUTCFullYear() - 1970) === 0) {
        return `${Math.abs(ageDate.getDate())} days`;
    }

    if (Math.abs(ageDate.getUTCFullYear() - 1970) === 0) {
        return `${Math.abs(ageDate.getMonth())} months`;
    }

    return `${Math.abs(ageDate.getUTCFullYear() - 1970)} years`;
};

export const setInvestigationFilters = (investigationFilter: InvestigationFilter) => {
    return {
        conditon: investigationFilter.conditions,
        programArea: investigationFilter.programAreas,
        jurisdiction: investigationFilter.jurisdictions,
        pregnancyTest: investigationFilter.pregnancyStatus,
        eventIdType: investigationFilter.eventId?.investigationEventType,
        eventId: investigationFilter.eventId?.id,
        eventDateType: investigationFilter.eventDate?.type,
        from: investigationFilter.eventDate?.from,
        to: investigationFilter.eventDate?.to,
        createdBy: investigationFilter.createdBy,
        lastUpdatedBy: investigationFilter.lastUpdatedBy,
        entityType: investigationFilter.providerFacilitySearch?.entityType,
        id: investigationFilter.providerFacilitySearch?.id,
        investigationStatus: investigationFilter.investigationStatus,
        outbreakNames: investigationFilter.outbreakNames,
        case: investigationFilter.caseStatuses?.includeUnassigned,
        statusList: investigationFilter.caseStatuses?.statusList,
        processing: investigationFilter.processingStatuses?.includeUnassigned,
        processingStatus: investigationFilter.processingStatuses?.statusList,
        notification: investigationFilter.notificationStatuses?.includeUnassigned,
        notificationStatus: investigationFilter.notificationStatuses?.statusList
    };
};

export const setLabReportFilters = (labReportFilter: LabReportFilter) => {
    return {
        labprogramArea: labReportFilter.programAreas,
        labjurisdiction: labReportFilter.jurisdictions,
        labpregnancyTest: labReportFilter.pregnancyStatus,
        labeventIdType: labReportFilter.eventId?.labEventType,
        labeventId: labReportFilter.eventId?.labEventId,
        labeventDateType: labReportFilter.eventDate?.type,
        labfrom: labReportFilter.eventDate?.from,
        labto: labReportFilter.eventDate?.to,
        labcreatedBy: labReportFilter.createdBy,
        lablastUpdatedBy: labReportFilter.lastUpdatedBy,
        labentityType: labReportFilter.providerSearch?.providerType,
        labid: labReportFilter.providerSearch?.providerId,
        resultedTest: labReportFilter?.resultedTest,
        codedResult: labReportFilter?.codedResult,
        entryMethod: labReportFilter?.entryMethods,
        enteredBy: labReportFilter?.enteredBy,
        eventStatus: labReportFilter?.eventStatus,
        processingStatus: labReportFilter?.processingStatus
    };
};
