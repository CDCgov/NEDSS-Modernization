import { Navigate, RouteObject } from 'react-router';
import { ApolloWrapper } from 'providers/ApolloContext';
import { ReportPage } from './ReportPage';
import { ReportRun } from './run';

const routing: RouteObject[] = [
    {
        path: 'report',
        element: (
            <ApolloWrapper>
                <ReportPage />
            </ApolloWrapper>
        ),
        children: [
            { index: true, element: <Navigate to="run/1/2" /> },
            {
                path: 'run/:reportUid/:dataSourceUid',
                element: <ReportRun />,
            },
        ],
    },
];

export { routing };
