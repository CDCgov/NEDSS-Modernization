import OpenInvestigationsCard from './openInvestigations/OpenInvestigationsCard';
import PatientSummaryCard from './PatientSummaryCard';

const PatientSummary = () => {
    return (
        <div>
            <PatientSummaryCard />
            <OpenInvestigationsCard />
        </div>
    );
};

export default PatientSummary;
