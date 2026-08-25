import { useComponentSizing } from 'design-system/sizing';
import { ClassicModalProvider } from 'libs/classic';

import { PatientFileView } from '../PatientFileView';
import { usePatientFileData } from '../usePatientFileData';

import { PatientFileContactsNamedCard } from './contactsNamed/PatientFileContactsNamedCard';
import { PatientFileDocumentsCard } from './documents/PatientFileDocumentsCard';
import { PatientFileInvestigationsCard } from './investigations';
import { PatientFilePatientsNamedCard } from './patientsNamed/PatientFilePatientsNamedCard';
import { PatientFileBirthRecordCard } from './record/birth';
import { PatientFileLaboratoryReportsCard } from './reports/laboratory';
import { PatientFileMorbidityReportsCard } from './reports/morbidity';
import { PatientFileTreatmentsCard } from './treatments/PatientFileTreatmentsCard';
import { PatientFileVaccinationsCard } from './vaccinations/PatientFileVaccinationsCard';

const PatientFileEvents = () => {
    const { id, patient, events } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <ClassicModalProvider>
            <PatientFileView patient={patient} sizing={sizing}>
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
                    provider={events.get().vaccination}
                    sizing={sizing}
                />
                <PatientFileBirthRecordCard
                    id="birth-records"
                    patient={id}
                    provider={events.get().birthRecords}
                    sizing={sizing}
                />
                <PatientFileTreatmentsCard id="treatments" provider={events.get().treatment} sizing={sizing} />
                <PatientFileDocumentsCard id="documents" provider={events.get().documents} sizing={sizing} />
                <PatientFileContactsNamedCard
                    provider={events.get().contactNamed}
                    id="contact-named"
                    patient={patient}
                    sizing={sizing}
                />
                <PatientFilePatientsNamedCard
                    provider={events.get().patientNamed}
                    id="patient-named"
                    patient={patient}
                    sizing={sizing}
                />
            </PatientFileView>
        </ClassicModalProvider>
    );
};

export { PatientFileEvents };
