import { RouteObject } from 'react-router';
import { ReportRunPage } from './run';
import { RedirectHome } from 'routes';
import { FeatureLayout } from 'feature';
import { permitsAny, permissions, Permitted } from 'libs/permission';
import { PermittedLayout } from 'libs/permission/PermittedLayout';

const routing: RouteObject[] = [
    {
        path: 'report',
        element: <FeatureLayout guard={(features) => features?.report?.execution?.enabled} />,
        errorElement: <RedirectHome />,
        children: [
            {
                path: ':reportUid/:dataSourceUid/run',
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
                element: <PermittedLayout permission="REPORTADMIN-SYSTEM" />,
                children: [
                    {
                        path: 'add',
                        lazy: {
                            Component: async () => (await import('./management/configuration')).AddReportConfiguration,
                        },
                    },
                    {
                        path: ':reportUid/:dataSourceUid',
                        lazy: {
                            Component: async () => (await import('./management/configuration')).ViewReportConfiguration,
                        },
                    },
                ],
            },
        ],
    },
];

export { routing };
