import { FeatureGuard } from 'feature';
import { PageTitle } from 'page';
import { MatchConfigurationLandingPage } from './configuration/MatchConfigurationLandingPage';
import { DataElementConfig } from './data-elements/DataElementConfig';
import { MergeDetails } from './patient-merge/details/MergeDetails';
import { MergeLanding } from './patient-merge/landing/MergeLanding';
import { permitsAll, Permitted } from '../../libs/permission';
import { RedirectHome } from '../../routes';

const routing = [
    {
        path: '/deduplication/configuration',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <Permitted
                    permission={permitsAll('MERGE-PATIENT', 'FIND-PATIENT')}
                    fallback={<RedirectHome />}>
                    <PageTitle title="Person match configuration">
                        <MatchConfigurationLandingPage />
                    </PageTitle>
                </Permitted>
            </FeatureGuard>
        )
    },
    {
        path: '/deduplication/data_elements',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.enabled}>
                <Permitted
                    permission={permitsAll('MERGE-PATIENT', 'FIND-PATIENT')}
                    fallback={<RedirectHome />}>
                    <PageTitle title="Person match configuration">
                        <DataElementConfig />
                    </PageTitle>
                </Permitted>
            </FeatureGuard>
        )
    },
    {
        path: '/deduplication/merge',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.merge.enabled}>
                <Permitted
                    permission={permitsAll('MERGE-PATIENT', 'FIND-PATIENT')}
                    fallback={<RedirectHome />}>
                    <PageTitle title="Patient Merge">
                        <MergeLanding />
                    </PageTitle>
                </Permitted>
            </FeatureGuard>
        )
    },
    {
        path: '/deduplication/merge/:matchId',
        element: (
            <FeatureGuard guard={(features) => features?.deduplication?.merge.enabled}>
                <Permitted
                    permission={permitsAll('MERGE-PATIENT', 'FIND-PATIENT')}
                    fallback={<RedirectHome />}>
                    <PageTitle title="Patient matches requiring review">
                        <MergeDetails />
                    </PageTitle>
                </Permitted>
            </FeatureGuard>
        )
    }
];

export { routing };
