import { ConfigurationSetup } from './configuration/ConfigurationSetup';
import { DataElementConfig } from './data-elements/DataElementConfig';
import { FeatureGuard } from 'feature';

const routing = [
    {
        path: '/deduplication/configuration',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <ConfigurationSetup />
            </FeatureGuard>
        )
    },
    {
        path: '/deduplication/data_elements',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <DataElementConfig />
            </FeatureGuard>
        )
    }
];

export { routing };
