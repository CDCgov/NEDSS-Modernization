import { useComponentSizing } from 'design-system/sizing';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileInvestigationsCard } from './investigations';
import { PatientFileLaboratoryReportsCard } from './reports/laboratory';
import { PatientFileMorbidityReportsCard } from './reports/morbidity';
import { PatientFileVaccinationsCard } from './vaccinations/PatientFileVaccinationsCard';

const PatientFileEvents = () => {
    const { id, events } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <>
            <PatientFileInvestigationsCard
                id="investigations"
                patient={id}
                provider={events.get().investigations}
                sizing={sizing}
            />
            <PatientFileLaboratoryReportsCard
                id="laboratory-reports"
                patient={id}
                provider={events.get().reports.laboratory}
                sizing={sizing}
            />
            <PatientFileMorbidityReportsCard
                id="morbidity-reports"
                patient={id}
                provider={events.get().reports.morbidity}
                sizing={sizing}
            />
            <PatientFileVaccinationsCard
                id="vaccinations"
                patient={id}
                provider={events.get().reports.vaccination}
                sizing={sizing}
            />
        </>
    );
};

export { PatientFileEvents };
