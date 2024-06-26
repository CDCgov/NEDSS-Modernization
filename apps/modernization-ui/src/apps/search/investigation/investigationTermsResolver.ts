import { Term, fromSelectable, fromSelectables, fromValue } from 'apps/search/terms';
import { InvestigationFilterEntry } from './InvestigationFormTypes';

const REPORTING_PROVIDER_TERM = {
    source: 'entityType',
    title: 'ENTITY TYPE',
    name: 'Reporting provider',
    value: 'PROVIDER'
};

const REPORTING_FACILITY_TERM = {
    source: 'entityType',
    title: 'ENTITY TYPE',
    name: 'Reporting facility',
    value: 'FACILITY'
};

const conditions = fromSelectables('conditions', 'CONDITION');
const programAreas = fromSelectables('programAreas', 'PROGRAM AREA');
const jurisdictions = fromSelectables('jurisdictions', 'JURISDICTION');

const outbreaks = fromSelectables('outbreaks', 'OUTBREAK');
const caseStatus = fromSelectables('caseStatuses', 'CASE STATUS');
const processingStatus = fromSelectables('processingStatuses', 'PROCESSING STATUS');
const notificationStatus = fromSelectables('notificationStatuses', 'NOTIFICATION STATUS');

const investigationTermsResolver = (entry: InvestigationFilterEntry): Term[] => {
    const terms: Term[] = [];

    conditions(entry.conditions).forEach((item) => terms.push(item));
    programAreas(entry.programAreas).forEach((item) => terms.push(item));

    jurisdictions(entry.jurisdictions).forEach((item) => terms.push(item));

    if (entry.pregnancyStatus) {
        terms.push(fromSelectable('pregnancyStatus', 'PREGNANCY STATUS')(entry.pregnancyStatus));
    }

    if (entry.identification) {
        terms.push(fromSelectable('identification.type', 'INVESTIGATION EVENT TYPE')(entry.identification.type));
        terms.push(fromValue('identification.value', 'EVENT ID')(entry.identification.value));
    }

    if (entry.eventDate) {
        terms.push(fromSelectable('eventDate.type', 'DATE TYPE')(entry.eventDate.type));
        terms.push(fromValue('eventDate.from', 'FROM')(entry.eventDate.from));
        terms.push(fromValue('eventDate.to', 'TO')(entry.eventDate.to));
    }

    if (entry.createdBy) {
        terms.push(fromSelectable('createdBy', 'CREATED BY')(entry.createdBy));
    }

    if (entry.updatedBy) {
        terms.push(fromSelectable('updatedBy', 'LAST UPDATED BY')(entry.updatedBy));
    }

    if (entry.reportingFacility) {
        terms.push(REPORTING_FACILITY_TERM);
        terms.push(fromSelectable('reportingFacility', 'ENTITY ID')(entry.reportingFacility));
    }

    if (entry.reportingProvider) {
        terms.push(REPORTING_PROVIDER_TERM);
        terms.push(fromSelectable('reportingProvider', 'ENTITY ID')(entry.reportingProvider));
    }

    if (entry.investigationStatus) {
        terms.push(fromSelectable('investigationStatus', 'INVESTIGATION STATUS')(entry.investigationStatus));
    }

    if (entry.investigator) {
        terms.push(fromSelectable('investigator', 'INVESTIGATOR')(entry.investigator));
    }

    outbreaks(entry.outbreaks).forEach((item) => terms.push(item));
    caseStatus(entry.caseStatuses).forEach((item) => terms.push(item));
    processingStatus(entry.processingStatuses).forEach((item) => terms.push(item));
    notificationStatus(entry.notificationStatuses).forEach((item) => terms.push(item));

    return terms;
};

export { investigationTermsResolver };
