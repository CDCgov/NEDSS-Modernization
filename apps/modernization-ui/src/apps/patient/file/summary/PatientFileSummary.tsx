import { usePatientFileData } from '../usePatientFileData';
import { usePatientFileSummary } from './usePatientFileSummary';
import { PatientSummaryContent } from './PatientSummaryContent';
import { PatientDocumentRequiringReview } from './documentRequiringReview';
import { OpenInvestigationsCard } from './openInvestigations';

export const PatientFileSummary = () => {
    const { id } = usePatientFileData();

    const { summary } = usePatientFileSummary(id);
    return (
        <>
            <PatientSummaryContent summary={summary} />
            <OpenInvestigationsCard patient={id} />
            <PatientDocumentRequiringReview patient={id} />
        </>
    );
};
