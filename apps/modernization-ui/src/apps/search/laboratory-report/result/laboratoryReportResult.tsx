import { LabReport, LabReportPersonParticipation } from 'generated/graphql/schema';
import { displayName } from 'name';
import { Link } from 'react-router-dom';

const getPatient = (labReport: LabReport): LabReportPersonParticipation | undefined | null => {
    return labReport.personParticipations?.find((p) => p?.typeCd === 'PATSBJ');
};

const getPatientName = (labReport: LabReport) => {
    const patient = getPatient(labReport);

    return (
        <Link to={`/patient-profile/${patient?.shortId}`}>
            {(patient && displayName('short')({ first: patient.firstName, last: patient.lastName })) || 'No Data'}
        </Link>
    );
};

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

export {
    getPatient,
    getPatientName,
    getOrderingProviderName,
    getReportingFacility,
    getDescription,
    getAssociatedInvestigations
};
