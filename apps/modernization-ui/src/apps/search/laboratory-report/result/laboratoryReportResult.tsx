import { BasicPatient } from 'apps/search/basic';
import { LabReport } from 'generated/graphql/schema';
import { displayName } from 'name';
import { ReactNode } from 'react';
import { Maybe } from 'utils';
import { ResultedTestDisplay } from './ResultedTestDisplay';

const getPatient = (labReport: LabReport): Maybe<BasicPatient> =>
    labReport.personParticipations?.find((p) => p?.typeCd === 'PATSBJ');

const getOrderingProviderName = (labReport: LabReport): string | undefined => {
    const provider = labReport.personParticipations.find((p) => p.typeCd === 'ORD' && p.personCd === 'PRV');

    return provider && displayName('short')({ first: provider.firstName, last: provider.lastName });
};

const getReportingFacility = (labReport: LabReport): string | undefined => {
    return labReport.organizationParticipations.find((o) => o?.typeCd === 'AUT')?.name;
};

const getDescription = (labReport: LabReport): ReactNode => {
    return (
        <div>
            {labReport.tests.map((s, k) => (
                <ResultedTestDisplay key={k} test={s} />
            ))}
        </div>
    );
};

const getAssociatedInvestigations = (labReport: LabReport) =>
    labReport.associatedInvestigations
        ?.map((investigation) => `${investigation?.localId}\n${investigation?.cdDescTxt}\n`)
        .join('\n');

export { getAssociatedInvestigations, getDescription, getOrderingProviderName, getPatient, getReportingFacility };
