import { Term, fromSelectable, fromSelectables, fromValue } from 'apps/search/terms';
import { LabReportFilterEntry } from './labReportFormTypes';

const ORDERING_FACILITY_TERM = {
    source: 'entityType',
    title: 'ENTITY TYPE',
    name: 'Ordering facility',
    value: 'ORDERING_FACILITY'
};

const ORDERING_PROVIDER_TERM = {
    source: 'entityType',
    title: 'ENTITY TYPE',
    name: 'Ordering provider',
    value: 'ORDERING_PROVIDER'
};

const REPORTING_FACILITY_TERM = {
    source: 'entityType',
    title: 'ENTITY TYPE',
    name: 'Reporting facility',
    value: 'REPORTING_FACILITY'
};

const programAreas = fromSelectables('programAreas', 'PROGRAM AREA');
const jurisdictions = fromSelectables('jurisdictions', 'JURISDICTION');
const entryMethods = fromSelectables('entryMethods', 'ENTRY METHOD');
const enteredBy = fromSelectables('enteredBy', 'ENTERED BY');
const eventStatus = fromSelectables('eventStatus', 'EVENT STATUS');
const processingStatus = fromSelectables('processingStatus', 'PROCESSING STATUS');

const laboratoryReportTermsResolver = (entry: LabReportFilterEntry): Term[] => {
    const terms: Term[] = [];

    programAreas(entry.programAreas).forEach((item) => terms.push(item));

    jurisdictions(entry.jurisdictions).forEach((item) => terms.push(item));

    if (entry.pregnancyStatus) {
        terms.push(fromSelectable('pregnancyStatus', 'PREGNANCY TEST')(entry.pregnancyStatus));
    }

    if (entry.identification && entry.identification.type) {
        terms.push(fromSelectable('identification.type', 'EVENT TYPE')(entry.identification.type));
        terms.push(fromValue('identification.value', 'EVENT ID TYPE')(entry.identification.value));
    }

    if (entry.eventDate?.type) {
        terms.push(fromSelectable('eventDate.type', 'EVENT DATE TYPE')(entry.eventDate.type));
        terms.push(fromValue('eventDate.from', 'FROM')(entry.eventDate.from));
        terms.push(fromValue('eventDate.to', 'TO')(entry.eventDate.to));
    }

    entryMethods(entry.entryMethods).forEach((item) => terms.push(item));
    enteredBy(entry.enteredBy).forEach((item) => terms.push(item));
    eventStatus(entry.eventStatus).forEach((item) => terms.push(item));
    processingStatus(entry.processingStatus).forEach((item) => terms.push(item));

    if (entry.createdBy) {
        terms.push(fromSelectable('createdBy', 'CREATED BY')(entry.createdBy));
    }

    if (entry.updatedBy) {
        terms.push(fromSelectable('updatedBy', 'LAST UPDATED BY')(entry.updatedBy));
    }

    if (entry.orderingFacility) {
        terms.push(ORDERING_FACILITY_TERM);
        terms.push(fromSelectable('orderingFacility', 'ENTITY ID')(entry.orderingFacility));
    }

    if (entry.orderingProvider) {
        terms.push(ORDERING_PROVIDER_TERM);
        terms.push(fromSelectable('orderingProvider', 'ENTITY ID')(entry.orderingProvider));
    }

    if (entry.reportingFacility) {
        terms.push(REPORTING_FACILITY_TERM);
        terms.push(fromSelectable('reportingFacility', 'ENTITY ID')(entry.reportingFacility));
    }

    if (entry.codedResult) {
        terms.push(fromSelectable('codedResult', 'CODED RESULT')(entry.codedResult));
    }

    if (entry.resultedTest) {
        terms.push(fromSelectable('resultedTest', 'RESULTED TEST')(entry.resultedTest));
    }

    return terms;
};

export { laboratoryReportTermsResolver };
