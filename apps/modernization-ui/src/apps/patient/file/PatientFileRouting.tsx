import { FeatureGuard } from 'feature';

const routing = [
    {
        path: '/patient/:id',
        element: (
            <FeatureGuard guard={(features) => features.patient.file.enabled}>
                <></>
            </FeatureGuard>
        )
    }
];

export { routing };
