import { usePatient } from '../usePatient';
import { usePatientFileSummary } from './usePatientFileSummary';
import { PatientSummaryContent } from './PatientSummaryContent';

export const PatientFileSummary = () => {
    const { id } = usePatient();

    const { summary } = usePatientFileSummary(id);
    return (
        <body>
            <PatientSummaryContent summary={summary} />
        </body>
    );
};
