import { FeatureLayout } from 'feature';
import { LoadingBlock } from 'libs/loading/block';
import { permitsAny, permissions, Permitted } from 'libs/permission';
import { PermittedLayout } from 'libs/permission/PermittedLayout';
import { ErrorPage } from 'pages/error';
import { RouteObject } from 'react-router';
import { RedirectHome } from 'routes';

import { ReportRunPage } from './run';
import { loadReportConfiguration } from './utils/loadReportConfiguration';

const routing: RouteObject[] = [
    {
        path: 'report',
        element: <FeatureLayout guard={(features) => features?.report?.execution?.enabled} />,
        ErrorBoundary: ErrorPage,
        HydrateFallback: LoadingBlock,
        children: [
            {
                path: ':reportUid/:dataSourceUid/run',
                loader: loadReportConfiguration,
                element: (
                    <Permitted
                        permission={permitsAny(permissions.reports.run, permissions.reports.export)}
                        fallback={<RedirectHome />}
                    >
                        <ReportRunPage />
                    </Permitted>
                ),
            },
            {
                // data source and sections will be sibings to configuration in the future
                path: 'management/configuration',
                element: <PermittedLayout permission={permissions.system.report} />,
                children: [
                    {
                        path: 'add',
                        lazy: {
                            Component: async () => (await import('./management/configuration')).AddReportConfiguration,
                        },
                    },
                    {
                        path: ':reportUid/:dataSourceUid',
                        loader: loadReportConfiguration,
                        lazy: {
                            Component: async () => (await import('./management/configuration')).ViewReportConfiguration,
                        },
                    },
                    {
                        path: ':reportUid/:dataSourceUid/edit',
                        loader: loadReportConfiguration,
                        lazy: {
                            Component: async () => (await import('./management/configuration')).EditReportConfiguration,
                        },
                    },
                ],
            },
        ],
    },
];

export { routing };
