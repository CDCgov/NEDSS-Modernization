import { FeatureGuard } from 'feature';
import { PatientFile } from './PatientFile';
import { PageTitle } from 'page';

const routing = [
    {
        path: '/patient/:id',
        element: (
            <FeatureGuard guard={(features) => features.patient.file.enabled}>
                <PageTitle title="Patient file">
                    <PatientFile />
                </PageTitle>
            </FeatureGuard>
        )
    }
];

export { routing };
