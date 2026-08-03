import { Guarded } from 'libs/guard';
import { permissions } from 'libs/permission';
import { RedirectHome } from 'routes';

import { PatientFile } from './PatientFile';

const GuardedPatientFile = () => (
    <Guarded
        feature={(features) => features.patient.file.enabled}
        permission={permissions.patient.file}
        fallback={<RedirectHome />}
    >
        <PatientFile />
    </Guarded>
);

export { GuardedPatientFile };
