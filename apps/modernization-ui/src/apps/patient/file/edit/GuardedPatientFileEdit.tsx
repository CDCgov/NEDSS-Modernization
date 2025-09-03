import { RedirectHome } from 'routes';
import { permissions } from 'libs/permission';
import { Guarded } from 'libs/guard';
import { PatientFileEdit } from './PatientFileEdit';
import { usePatientFileData } from '../usePatientFileData';
import { Shown } from 'conditional-render';

const GuardedPatientFileEdit = () => {
    const { patient } = usePatientFileData();

    return (
        <Shown when={patient.status === 'ACTIVE'} fallback={<RedirectHome />}>
            <Guarded
                feature={(features) => features.patient.file.enabled}
                permission={permissions.patient.update}
                fallback={<RedirectHome />}>
                <PatientFileEdit />
            </Guarded>
        </Shown>
    );
};

export { GuardedPatientFileEdit };
