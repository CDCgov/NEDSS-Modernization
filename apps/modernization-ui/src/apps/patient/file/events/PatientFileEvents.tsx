import { InvestigationsCard } from './investigations';
import { usePatient } from '../usePatient';

const PatientFileEvents = () => {
    const { id } = usePatient();

    return (
        <>
            <InvestigationsCard patient={id} />
        </>
    );
};

export { PatientFileEvents };
