import { FeatureGuard } from 'feature';
import { DataElementConfig } from './data-elements/DataElementConfig';
import { MatchConfiguration } from './match-configuration/MatchConfiguration';

const routing = [
    {
        path: '/deduplication/match-configuration',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <MatchConfiguration />
            </FeatureGuard>
        )
    },
    {
        path: '/deduplication/data-elements',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <DataElementConfig />
            </FeatureGuard>
        )
    }
];

export { routing };
