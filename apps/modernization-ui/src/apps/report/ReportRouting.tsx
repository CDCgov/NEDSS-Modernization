import { RouteObject } from 'react-router';
import { ReportRunPage } from './run';
import { RedirectHome } from 'routes';
import { FeatureLayout } from 'feature';
import { permitsAny, permissions, Permitted } from 'libs/permission';
import { PermittedLayout } from 'libs/permission/PermittedLayout';
import { loadReportConfiguration } from './utils/loadReportConfiguration';
import { ErrorPage } from 'pages/error';
import { LoadingBlock } from 'libs/loading/block';

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
