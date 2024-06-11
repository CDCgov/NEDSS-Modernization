import { Navigate } from 'react-router-dom';

import { AdvancedSearch } from './advancedSearch/AdvancedSearch';

import { SearchPage } from './SearchPage';
import { PatientSearch } from './patient/PatientSearch';
import { LaboratoryReportSearch } from './laboratory-report';
import { InvestigationSearch } from './investigation';

const routing = [
    {
        path: '/advanced-search/:searchType?',
        element: <AdvancedSearch />
    },
    {
        path: 'search',
        element: <SearchPage />,
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
            }
        ]
    }
];

export { routing };
