import { PageProvider, PageTitle } from 'page';
import SystemManagementPage from './layout/SystemManagementPage';

const routing = [
    {
        path: '/system_management',
        element: (
            <PageProvider>
                <PageTitle title="System Management">
                    <SystemManagementPage />
                </PageTitle>
            </PageProvider>
        )
    }
];

export { routing };
