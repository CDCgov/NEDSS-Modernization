import PatientMatchConfiguration from './PatientMatchConfiguration';
import PatientMatchContextProvider from './context/PatientMatchContext';

const routing = [
    {
        path: '/patient-match-config',
        element: (
            <PatientMatchContextProvider>
                <PatientMatchConfiguration />
            </PatientMatchContextProvider>
        )
    }
];

export { routing };
