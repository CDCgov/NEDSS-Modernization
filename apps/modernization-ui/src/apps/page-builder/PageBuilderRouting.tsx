import { FeatureGuard, FeatureLayout } from 'feature';
import { PageProvider } from 'page';
import { Navigate, RouteObject } from 'react-router-dom';
import { PageLibrary } from './page/library/PageLibrary';
import { Edit } from './page/management/edit/Edit';
import { PreviewPage } from './page/management/preview';
import { PageDetails } from './page/management/preview/PageDetails/PageDetails';
import { AddNewPage } from './pages/AddNewPage/AddNewPage';
import { AddBusinessRule } from './pages/BusinessRulesLibrary/Add/AddBusinessRules';
import { BusinessRulesLibrary } from './pages/BusinessRulesLibrary/BusinessRulesLibrary';
import { EditBusinessRule } from './pages/BusinessRulesLibrary/Edit/EditBusinessRules';
import { ViewBusinessRule } from './pages/BusinessRulesLibrary/ViewBusinessRule/ViewBusinessRule';

const routing: RouteObject[] = [
    {
        path: '/page-builder',
        children: [
            {
                index: true,
                element: <Navigate to="pages" />
            },
            {
                path: 'pages',
                children: [
                    {
                        index: true,
                        element: (
                            <FeatureGuard guard={(features) => features?.pageBuilder?.page?.library?.enabled}>
                                <PageLibrary />
                            </FeatureGuard>
                        )
                    },
                    {
                        path: 'add',
                        element: (
                            <FeatureGuard
                                guard={(features) => features?.pageBuilder?.page?.management?.create?.enabled}>
                                <AddNewPage />
                            </FeatureGuard>
                        )
                    },
                    {
                        path: ':pageId',
                        element: (
                            <FeatureLayout
                                guard={(features) => features?.pageBuilder?.page?.management?.edit?.enabled}
                            />
                        ),
                        children: [
                            {
                                index: true,
                                element: <PreviewPage />
                            },
                            {
                                path: 'edit',
                                element: <Edit />
                            },
                            {
                                path: 'details',
                                element: <PageDetails />
                            },
                            {
                                path: 'business-rules',
                                children: [
                                    {
                                        index: true,
                                        element: (
                                            <PageProvider>
                                                <BusinessRulesLibrary />
                                            </PageProvider>
                                        )
                                    },
                                    {
                                        path: ':ruleId',
                                        element: <ViewBusinessRule />
                                    },
                                    {
                                        path: 'add',
                                        element: <AddBusinessRule />
                                    },
                                    {
                                        path: 'edit/:ruleId',
                                        element: <EditBusinessRule />
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    }
];

export { routing };
