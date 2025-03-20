import { MatchConfiguration } from './configuration/MatchConfiguration';
import { DataElementConfig } from './data-elements/DataElementConfig';
import { FeatureGuard } from 'feature';

const routing = [
    {
        path: '/deduplication/configuration',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <MatchConfiguration />
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
