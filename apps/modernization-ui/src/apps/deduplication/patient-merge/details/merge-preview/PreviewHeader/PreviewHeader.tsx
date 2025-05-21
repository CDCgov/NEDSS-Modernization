import { dummyPatientData, getLatestLegalName } from '../dummyData';
import { PatientFileHeader } from '../../../../../patient/file/PatientFileHeader';
import styles from './PreviewHeader.module.scss';

export const PreviewHeader = () => {
    const { patientId, name, selectedSexAndBirth } = dummyPatientData;
    const latestLegalName = getLatestLegalName(name);

    const patient = {
        id: Number(patientId),
        patientId: Number(patientId),
        name: latestLegalName,
        birthday: selectedSexAndBirth.dateOfBirth,
        deceasedOn: undefined,
        sex: selectedSexAndBirth.birthSex,
        local: 'local-id-value',
        status: 'status',
        deletability: 'Deletable' as const
    };

    return (
        <div className={styles.headerOverride}>
            <PatientFileHeader patient={patient} actions={null} />
        </div>
    );
};
