import { Navigate, RouteObject } from 'react-router';
import { ReportRun } from './run';
import { RedirectHome } from 'routes';
import { FeatureLayout } from 'feature';
import { Permitted } from 'libs/permission';

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
                    <Permitted permission={'RUNREPORT-REPORTING'} fallback={<RedirectHome />}>
                        <ReportRun />
                    </Permitted>
                ),
            },
        ],
    },
];

export { routing };
