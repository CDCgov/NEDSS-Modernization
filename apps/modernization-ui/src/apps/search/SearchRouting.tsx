import { Navigate } from 'react-router-dom';
import { AdvancedSearch } from './advancedSearch/AdvancedSearch';
import { PatientSearch } from './patient';
import { LaboratoryReportSearch } from './laboratory-report';
import { InvestigationSearch } from './investigation';

const routing = [
    {
        path: '/advanced-search/:searchType?',
        element: <AdvancedSearch />
    },
    {
        path: 'search',
        children: [
            { index: true, element: <Navigate to="patient" /> },
            {
                path: 'patient',
                element: <PatientSearch />
            },
            {
                path: 'laboratory-search',
                element: <LaboratoryReportSearch />
            },
            {
                path: 'investigation',
                element: <InvestigationSearch />
            }
        ]
    }
];

export { routing };
