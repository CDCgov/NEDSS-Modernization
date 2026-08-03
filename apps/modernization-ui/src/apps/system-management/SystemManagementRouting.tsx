import { PageTitle } from 'page';

import { FeatureGuard } from '../../feature';

import SystemManagementPage from './layout/SystemManagementPage';

const routing = [
    {
        path: '/system/management',
        element: (
            <FeatureGuard guard={(features) => features?.system?.management?.enabled}>
                <PageTitle title="System Management">
                    <SystemManagementPage />
                </PageTitle>
            </FeatureGuard>
        ),
    },
];

export { routing };
