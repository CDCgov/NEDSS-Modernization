import { MatchConfiguration } from './configuration/MatchConfiguration';
import { FeatureGuard } from 'feature';

const routing = [
    {
        path: '/deduplication/configuration',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <MatchConfiguration />
            </FeatureGuard>
        )
    }
];

export { routing };
