import { LabReport } from 'generated/graphql/schema';
import { BasicPatient } from 'apps/search/basic';
import { Maybe } from 'utils';
import { displayName } from 'name';

const getPatient = (labReport: LabReport): Maybe<BasicPatient> =>
    labReport.personParticipations?.find((p) => p?.typeCd === 'PATSBJ');

const getOrderingProviderName = (labReport: LabReport): string | undefined => {
    const provider = labReport.personParticipations.find((p) => p.typeCd === 'ORD' && p.personCd === 'PRV');

    return provider && displayName('short')({ first: provider.firstName, last: provider.lastName });
};

const getReportingFacility = (labReport: LabReport): string | undefined => {
    return labReport.organizationParticipations.find((o) => o?.typeCd === 'AUT')?.name;
};

const getDescription = (labReport: LabReport): string | undefined => {
    const observation = labReport.observations?.find((o) => o?.altCd && o?.displayName && o?.cdDescTxt);

    return observation && `${observation.cdDescTxt} = ${observation.displayName}`;
};

const getAssociatedInvestigations = (labReport: LabReport) =>
    labReport.associatedInvestigations
        ?.map((investigation) => `${investigation?.localId}\n${investigation?.cdDescTxt}\n`)
        .join('\n');

export { getPatient, getOrderingProviderName, getReportingFacility, getDescription, getAssociatedInvestigations };
