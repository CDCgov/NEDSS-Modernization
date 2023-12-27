import { asLocalDate } from 'date';
import { FindLabReportsForPatientQuery, PatientLabReport as GraphQLPatientLabReport } from 'generated/graphql/schema';
import { mapNonNull, orNull } from 'utils';
import { AssociatedWith, PatientLabReport, TestResult } from './PatientLabReport';

type Result = FindLabReportsForPatientQuery['findLabReportsForPatient'];

const getReportingFacility = (content: GraphQLPatientLabReport): string | undefined | null => {
    return content.organizationParticipations?.find((o) => o?.typeCd === 'AUT')?.name;
};

const getOrderingProviderName = (content: GraphQLPatientLabReport): string | null => {
    const provider = content.personParticipations?.find((p) => p?.typeCd === 'ORD' && p?.personCd === 'PRV');
    if (provider) {
        return `${provider.firstName} ${provider.lastName}`;
    } else {
        return null;
    }
};

const getOrderingFacility = (content: GraphQLPatientLabReport): string | undefined | null => {
    return content.organizationParticipations?.find((o) => o?.typeCd === 'ORD')?.name;
};

const getAssociations = (content: GraphQLPatientLabReport): AssociatedWith[] => {
    return content.associatedInvestigations.map((investigation) => ({
        id: String(investigation.publicHealthCaseUid),
        local: investigation.localId,
        condition: investigation.cdDescTxt
    }));
};

const getTestResults = (content: GraphQLPatientLabReport): TestResult[] => {
    return content.observations
        .filter((result) => result.domainCd === 'Result')
        .map((result) => ({ test: result.cdDescTxt, result: orNull(result.displayName) }));
};

const internalized = (content: GraphQLPatientLabReport): PatientLabReport | null => {
    if (content) {
        return {
            report: String(content.observationUid),
            receivedOn: asLocalDate(content.addTime),
            reportingFacility: orNull(getReportingFacility(content)),
            orderingProvider: getOrderingProviderName(content),
            orderingFacility: orNull(getOrderingFacility(content)),
            collectedOn: asLocalDate(content.effectiveFromTime),
            results: getTestResults(content),
            associatedWith: getAssociations(content),
            programArea: content.programAreaCd,
            jurisdiction: content.jurisdictionCodeDescTxt,
            event: content.localId
        };
    }
    return null;
};

export const transform = (result: Result): PatientLabReport[] => mapNonNull(internalized, result);
