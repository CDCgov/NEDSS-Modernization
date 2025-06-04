import { PageTitle } from 'page';
import SystemManagementPage from './layout/SystemManagementPage';
import { FeatureGuard } from '../../feature';

const routing = [
    {
        path: '/system/management',
        element: (
            <FeatureGuard guard={(features) => features?.system?.management?.enabled}>
                <PageTitle title="System Management">
                    <SystemManagementPage />
                </PageTitle>
            </FeatureGuard>
        )
    }
];

export { routing };
