import { FeatureLayout } from 'feature';
import { Navigate, RouteObject } from 'react-router';

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
                        lazy: {
                            Component: async () => (await import('./page/library')).GuardedPageLibrary
                        }
                    },
                    {
                        path: 'add',
                        lazy: {
                            Component: async () => (await import('./pages/AddNewPage')).GuardedAddNewPage
                        }
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
                                lazy: {
                                    Component: async () => (await import('./page/management/preview')).PreviewPage
                                }
                            },
                            {
                                path: 'edit',
                                lazy: {
                                    Component: async () => (await import('./page/management/edit/Edit')).Edit
                                }
                            },
                            {
                                path: 'details',
                                lazy: {
                                    Component: async () =>
                                        (await import('./page/management/preview/PageDetails/PageDetails')).PageDetails
                                }
                            },
                            {
                                path: 'business-rules',
                                children: [
                                    {
                                        index: true,
                                        lazy: {
                                            Component: async () =>
                                                (await import('./pages/BusinessRulesLibrary/BusinessRulesLibrary'))
                                                    .BusinessRulesLibrary
                                        }
                                    },
                                    {
                                        path: ':ruleId',
                                        lazy: {
                                            Component: async () =>
                                                (
                                                    await import(
                                                        './pages/BusinessRulesLibrary/ViewBusinessRule/ViewBusinessRule'
                                                    )
                                                ).ViewBusinessRule
                                        }
                                    },
                                    {
                                        path: 'add',
                                        lazy: {
                                            Component: async () =>
                                                (await import('./pages/BusinessRulesLibrary/Add/AddBusinessRules'))
                                                    .AddBusinessRule
                                        }
                                    },
                                    {
                                        path: 'edit/:ruleId',
                                        lazy: {
                                            Component: async () =>
                                                (await import('./pages/BusinessRulesLibrary/Edit/EditBusinessRules'))
                                                    .EditBusinessRule
                                        }
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
