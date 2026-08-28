import { Navigate, RouteObject } from 'react-router';

import { FeatureLayout } from 'feature';
import { ErrorPage } from 'pages/error';

const routing: RouteObject[] = [
    {
        path: '/page-builder',
        ErrorBoundary: ErrorPage,
        children: [
            {
                index: true,
                element: <Navigate to="pages" />,
            },
            {
                path: 'pages',
                children: [
                    {
                        index: true,
                        lazy: {
                            Component: async () => (await import('./page/library')).GuardedPageLibrary,
                        },
                    },
                    {
                        path: 'add',
                        lazy: {
                            Component: async () => (await import('./pages/AddNewPage')).GuardedAddNewPage,
                        },
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
                                    Component: async () => (await import('./page/management/preview')).PreviewPage,
                                },
                            },
                            {
                                path: 'edit',
                                lazy: {
                                    Component: async () => (await import('./page/management/edit/Edit')).Edit,
                                },
                            },
                            {
                                path: 'details',
                                lazy: {
                                    Component: async () =>
                                        (await import('./page/management/preview/PageDetails/PageDetails')).PageDetails,
                                },
                            },
                            {
                                path: 'business-rules',
                                children: [
                                    {
                                        index: true,
                                        lazy: {
                                            Component: async () =>
                                                (await import('./pages/BusinessRulesLibrary/BusinessRulesLibrary'))
                                                    .BusinessRulesLibrary,
                                        },
                                    },
                                    {
                                        path: ':ruleId',
                                        lazy: {
                                            Component: async () => {
                                                const { ViewBusinessRule } =
                                                    // eslint-disable-next-line max-len
                                                    await import('./pages/BusinessRulesLibrary/ViewBusinessRule/ViewBusinessRule');
                                                return ViewBusinessRule;
                                            },
                                        },
                                    },
                                    {
                                        path: 'add',
                                        lazy: {
                                            Component: async () =>
                                                (await import('./pages/BusinessRulesLibrary/Add/AddBusinessRules'))
                                                    .AddBusinessRule,
                                        },
                                    },
                                    {
                                        path: 'edit/:ruleId',
                                        lazy: {
                                            Component: async () =>
                                                (await import('./pages/BusinessRulesLibrary/Edit/EditBusinessRules'))
                                                    .EditBusinessRule,
                                        },
                                    },
                                ],
                            },
                        ],
                    },
                ],
            },
        ],
    },
];

export { routing };
