import { CaseStatus, InvestigationFilter, PregnancyStatus } from 'generated/graphql/schema';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { asValue, asValues } from 'options/selectable';

export const transformObject = (data: InvestigationFilterEntry): InvestigationFilter => {
    return {
        ...data,
        createdBy: data.createdBy ? (asValue(data.createdBy) as string) : undefined,
        jurisdictions: data.jurisdictions ? (asValues(data.jurisdictions) as string[]) : undefined,
        conditions: data.conditions ? (asValues(data.conditions) as string[]) : undefined,
        lastUpdatedBy: data.lastUpdatedBy ? (asValue(data.lastUpdatedBy) as string) : undefined,
        investigatorId: data.investigatorId ? (asValue(data.investigatorId) as string) : undefined,
        pregnancyStatus: data.pregnancyStatus ? (asValue(data.pregnancyStatus) as PregnancyStatus) : undefined,
        caseStatuses: data.caseStatuses ? (asValues(data.caseStatuses) as CaseStatus[]) : undefined,
        outbreakNames: data.outbreakNames ? (asValues(data.outbreakNames) as string[]) : undefined,
        programAreas: data.programAreas ? (asValues(data.programAreas) as string[]) : undefined
    };
};
