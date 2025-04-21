import { MatchConfigurationLandingPage } from './configuration/MatchConfigurationLandingPage';
import { DataElementConfig } from './data-elements/DataElementConfig';
import { FeatureGuard } from 'feature';
import { MergeLanding } from './patient-merge/landing/MergeLanding';
import { PageTitle } from 'page';

const routing = [
    {
        path: '/deduplication/configuration',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <PageTitle title="Person match configuration">
                    <MatchConfigurationLandingPage />
                </PageTitle>
            </FeatureGuard>
        )
    },
    {
        path: '/deduplication/data_elements',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <PageTitle title="Person match configuration">
                    <DataElementConfig />
                </PageTitle>
            </FeatureGuard>
        )
    },
    {
        path: '/deduplication/merge',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.merge.enabled}>
                <PageTitle title="Patient Merge">
                    <MergeLanding />
                </PageTitle>
            </FeatureGuard>
        )
    }
];

export { routing };
