import { RedirectHome } from 'routes';
import { permissions } from 'libs/permission';
import { Guarded } from 'libs/guard';
import { PatientFileEdit } from './PatientFileEdit';

const GuardedPatientFileEdit = () => (
    <Guarded
        feature={(features) => features.patient.file.enabled}
        permission={permissions.patient.update}
        fallback={<RedirectHome />}>
        <PatientFileEdit />
    </Guarded>
);

export { GuardedPatientFileEdit };
