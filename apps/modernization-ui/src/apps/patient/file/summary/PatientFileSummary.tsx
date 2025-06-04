import { usePatient } from 'apps/patient/file/usePatient';
import { usePatientFileSummary } from './usePatientFileSummary';
import { PatientSummaryContent } from './PatientSummaryContent';
import { PatientDocumentRequiringReview } from './documentRequiringReview';
import { OpenInvestigationsCard } from './openInvestigations';

export const PatientFileSummary = () => {
    const { id } = usePatient();

    const { summary } = usePatientFileSummary(id);
    return (
        <>
            <PatientSummaryContent summary={summary} />
            <OpenInvestigationsCard patient={id} />
            <PatientDocumentRequiringReview patient={id} />
        </>
    );
};
