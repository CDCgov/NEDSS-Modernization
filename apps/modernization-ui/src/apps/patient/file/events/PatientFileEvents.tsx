import { usePatientFileData } from '../usePatientFileData';
import { InvestigationsCard } from './investigations';
import { LabReportsCard } from './reports/laboratory';

const PatientFileEvents = () => {
    const { id } = usePatientFileData();

    return (
        <>
            <InvestigationsCard patient={id} />
            <LabReportsCard patient={id} />
        </>
    );
};

export { PatientFileEvents };
