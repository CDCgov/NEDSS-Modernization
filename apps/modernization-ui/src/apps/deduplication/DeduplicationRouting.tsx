import { ConfigurationSetup } from './configuration/ConfigurationSetup';
import { FeatureGuard } from 'feature';

const routing = [
    {
        path: '/deduplication/configuration',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <ConfigurationSetup />
            </FeatureGuard>
        )
    }
];

export { routing };
