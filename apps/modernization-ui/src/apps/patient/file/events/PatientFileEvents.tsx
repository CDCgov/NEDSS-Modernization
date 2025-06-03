import { InvestigationsCard } from './investigations';
import { usePatient } from '../usePatient';
import { LabReportsCard } from './labReports';

const PatientFileEvents = () => {
    const { id } = usePatient();

    return (
        <>
            <InvestigationsCard patient={id} />
            <LabReportsCard patient={id} />
        </>
    );
};

export { PatientFileEvents };
