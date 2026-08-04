import { Shown } from 'conditional-render';
import { Guarded } from 'libs/guard';
import { permissions } from 'libs/permission';
import { RedirectHome } from 'routes';

import { usePatientFileData } from '../usePatientFileData';

import { PatientFileEdit } from './PatientFileEdit';

const GuardedPatientFileEdit = () => {
    const { patient } = usePatientFileData();

    return (
        <Shown when={patient.status === 'ACTIVE'} fallback={<RedirectHome />}>
            <Guarded
                feature={(features) => features.patient.file.enabled}
                permission={permissions.patient.update}
                fallback={<RedirectHome />}
            >
                <PatientFileEdit />
            </Guarded>
        </Shown>
    );
};

export { GuardedPatientFileEdit };
