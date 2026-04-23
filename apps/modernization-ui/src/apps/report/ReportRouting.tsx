import { Navigate, RouteObject } from 'react-router';
import { ReportRunPage } from './run';
import { RedirectHome } from 'routes';
import { FeatureLayout } from 'feature';
import { permitsAny, permissions, Permitted } from 'libs/permission';

const routing: RouteObject[] = [
    {
        path: 'report/:reportUid/:dataSourceUid',
        element: <FeatureLayout guard={(features) => features?.report?.execution?.enabled} />,
        errorElement: <RedirectHome />,
        children: [
            { index: true, element: <Navigate to="run" replace /> },
            {
                path: 'run',
                element: (
                    <Permitted
                        permission={permitsAny(permissions.reports.run, permissions.reports.export)}
                        fallback={<RedirectHome />}
                    >
                        <ReportRunPage />
                    </Permitted>
                ),
            },
        ],
    },
];

export { routing };
