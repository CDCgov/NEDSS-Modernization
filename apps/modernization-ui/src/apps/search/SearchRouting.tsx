import { Navigate } from 'react-router';
import { ApolloWrapper } from 'providers/ApolloContext';
import { SearchPage } from './SearchPage';
import { PatientSearch } from './patient/PatientSearch';
import { LaboratoryReportSearch } from './laboratory-report';
import { InvestigationSearch } from './investigation';
import { SimpleSearch } from './simple';

const routing = [
    {
        path: 'search',
        element: (
            <ApolloWrapper>
                <SearchPage />
            </ApolloWrapper>
        ),
        children: [
            { index: true, element: <Navigate to="patients" /> },
            {
                path: 'patients',
                element: <PatientSearch />
            },
            {
                path: 'lab-reports',
                element: <LaboratoryReportSearch />
            },
            {
                path: 'investigations',
                element: <InvestigationSearch />
            },
            {
                path: 'simple/:type/:criteria',
                element: <SimpleSearch />
            }
        ]
    }
];

export { routing };
