import { InvestigationsCard } from './investigations';
import { usePatient } from '../usePatient';
import { LabReportsCard } from './reports/laboratory';

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
