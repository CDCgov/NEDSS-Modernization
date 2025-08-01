import { useComponentSizing } from 'design-system/sizing';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileInvestigationsCard } from './investigations';
import { PatientFileLaboratoryReportsCard } from './reports/laboratory';
import { PatientFileMorbidityReportsCard } from './reports/morbidity';
import { PatientFileVaccinationsCard } from './vaccinations/PatientFileVaccinationsCard';
import { PatientFileTreatmentsCard } from './treatments/PatientFileTreatmentsCard';
import { PatientFileContactsNamedCard } from './contactsNamed/PatientFileContactsNamedCard';
import { initial } from 'libs/events/contacts/contactsNamed';

const PatientFileEvents = () => {
    const { id, patient, events } = usePatientFileData();
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
            <PatientFileTreatmentsCard id="treatments" provider={events.get().reports.treatment} sizing={sizing} />

            <PatientFileContactsNamedCard provider={initial()} id={'contact-named'} patient={patient} sizing={sizing} />
        </>
    );
};

export { PatientFileEvents };
