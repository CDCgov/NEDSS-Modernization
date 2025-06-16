import { Navigate } from 'react-router';
import { SearchPage } from './SearchPage';
import { PatientSearch } from './patient/PatientSearch';
import { LaboratoryReportSearch } from './laboratory-report';
import { InvestigationSearch } from './investigation';
import { SimpleSearch } from './simple';

const routing = [
    {
        path: 'search',
        children: [
            { index: true, element: <Navigate to="patients" /> },
            {
                path: 'patients',
                element: <SearchPage pageSizePreferenceKey="patients-search-page-size" />,
                children: [{ index: true, element: <PatientSearch /> }]
            },
            {
                path: 'lab-reports',
                element: <SearchPage pageSizePreferenceKey="lab-reports-search-page-size" />,
                children: [{ index: true, element: <LaboratoryReportSearch /> }]
            },
            {
                path: 'investigations',
                element: <SearchPage pageSizePreferenceKey="investigations-search-page-size" />,
                children: [{ index: true, element: <InvestigationSearch /> }]
            },
            {
                path: 'simple/:type/:criteria',
                element: <SearchPage pageSizePreferenceKey="simple-search-page-size" />,
                children: [{ index: true, element: <SimpleSearch /> }]
            }
        ]
    }
];

export { routing };
