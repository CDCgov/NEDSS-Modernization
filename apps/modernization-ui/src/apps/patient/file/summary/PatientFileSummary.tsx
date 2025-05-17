import { usePatient } from '../usePatient';
import { usePatientFileSummary } from './usePatientFileSummary';
import { PatientSummaryContent } from './PatientSummaryContent';
import OpenInvestigationsCard from './openInvestigations/OpenInvestigationsCard';

export const PatientFileSummary = () => {
    const { id } = usePatient();

    const { summary } = usePatientFileSummary(id);
    return (
        <div>
            <PatientSummaryContent summary={summary} />
            <br />
            <OpenInvestigationsCard />
        </div>
    );
};
