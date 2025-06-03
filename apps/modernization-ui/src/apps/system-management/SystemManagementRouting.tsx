import { PageTitle } from 'page';
import SystemManagementPage from './layout/SystemManagementPage';
import { FeatureGuard } from "../../feature";

const routing = [
    {
        path: '/system_management',
        element: (
            <FeatureGuard guard={(features) => features?.systemManagement?.enabled}>
                <PageTitle title="System Management">
                    <SystemManagementPage />
                </PageTitle>
            </FeatureGuard>
        )
    }
];

export { routing };
