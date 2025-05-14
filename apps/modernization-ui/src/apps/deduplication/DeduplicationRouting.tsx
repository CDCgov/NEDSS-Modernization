import { FeatureGuard } from 'feature';
import { PageTitle } from 'page';
import { MatchConfigurationLandingPage } from './configuration/MatchConfigurationLandingPage';
import { DataElementConfig } from './data-elements/DataElementConfig';
import { MergeDetails } from './patient-merge/details/MergeDetails';
import { MergeLanding } from './patient-merge/landing/MergeLanding';

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
    },
    {
        path: '/deduplication/merge/:patientId',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.merge.enabled}>
                <PageTitle title="Patient matches requiring review">
                    <MergeDetails />
                </PageTitle>
            </FeatureGuard>
        )
    }
];

export { routing };
