import { Term, fromSelectable, fromSelectables, fromValue } from 'apps/search/terms';
import { InvestigationFilterEntry } from './InvestigationFormTypes';

const conditions = fromSelectables('conditions', 'CONDITION');
const programAreas = fromSelectables('programAreas', 'PROGRAM AREA');
const jurisdictions = fromSelectables('jurisdictions', 'JURISDICTION');

const outbreaks = fromSelectables('outbreaks', 'OUTBREAK NAME');
const caseStatus = fromSelectables('caseStatuses', 'CASE STATUS');
const processingStatus = fromSelectables('processingStatuses', 'PROCESSING STATUS');
const notificationStatus = fromSelectables('notificationStatuses', 'NOTIFICATION STATUS');

const investigationTermsResolver = (entry: InvestigationFilterEntry): Term[] => {
    const terms: Term[] = [];

    conditions(entry.conditions ?? []).forEach((item) => terms.push(item));

    if (entry.programAreas) {
        programAreas(entry.programAreas ?? []).forEach((item) => terms.push(item));
    }

    if (entry.jurisdictions) {
        jurisdictions(entry.jurisdictions ?? []).forEach((item) => terms.push(item));
    }

    if (entry.pregnancyStatus) {
        terms.push(fromSelectable('pregnancyStatus', 'PREGNANCY STATUS')(entry.pregnancyStatus));
    }

    if (entry.identification && entry.identification?.type) {
        terms.push(fromSelectable('identification.type', 'EVENT ID TYPE')(entry.identification.type));
        if (entry.identification?.value) {
            terms.push(fromValue('identification.value', 'EVENT ID')(entry.identification?.value));
        }
    }

    if (entry.eventDate && entry.eventDate?.from && entry.eventDate?.to) {
        terms.push(fromSelectable('eventDate.type', 'EVENT DATE TYPE')(entry.eventDate.type));
        if (entry.eventDate?.from) {
            terms.push(fromValue('eventDate.from', 'FROM')(entry.eventDate.from));
        }
        if (entry.eventDate?.to) {
            terms.push(fromValue('eventDate.to', 'TO')(entry.eventDate.to));
        }
    }

    if (entry.createdBy) {
        terms.push(fromSelectable('createdBy', 'CREATED BY')(entry.createdBy));
    }

    if (entry.updatedBy) {
        terms.push(fromSelectable('updatedBy', 'LAST UPDATED BY')(entry.updatedBy));
    }

    if (entry.reportingProviderId) {
        terms.push(fromSelectable('reportingProviderId', 'REPORTING PROVIDER')(entry.reportingProviderId));
    }

    if (entry.reportingFacilityId) {
        terms.push(fromSelectable('reportingFacilityId', 'REPORTING FACILITY')(entry.reportingFacilityId));
    }

    if (entry.investigationStatus) {
        terms.push(fromSelectable('investigationStatus', 'INVESTIGATION STATUS')(entry.investigationStatus));
    }

    if (entry.investigator) {
        terms.push(fromSelectable('investigator', 'INVESTIGATOR')(entry.investigator));
    }

    if (entry.outbreaks) {
        outbreaks(entry.outbreaks).forEach((item) => terms.push(item));
    }
    if (entry.caseStatuses) {
        caseStatus(entry.caseStatuses).forEach((item) => terms.push(item));
    }

    if (entry.processingStatuses) {
        processingStatus(entry.processingStatuses).forEach((item) => terms.push(item));
    }

    if (entry.notificationStatuses) {
        notificationStatus(entry.notificationStatuses).forEach((item) => terms.push(item));
    }

    return terms;
};

export { investigationTermsResolver };
