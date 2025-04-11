import { FeatureGuard } from 'feature';
import { PatientFile } from './PatientFile';

const routing = [
    {
        path: '/patient/:id',
        element: (
            <FeatureGuard guard={(features) => features.patient.file.enabled}>
                <PatientFile />
            </FeatureGuard>
        )
    }
];

export { routing };
